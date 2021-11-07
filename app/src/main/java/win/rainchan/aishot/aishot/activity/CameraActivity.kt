package win.rainchan.aishot.aishot.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import splitties.permissions.ensurePermission
import splitties.toast.toast
import win.rainchan.aishot.aishot.APP
import win.rainchan.aishot.aishot.ai.camera.CameraSource
import win.rainchan.aishot.aishot.ai.data.Device
import win.rainchan.aishot.aishot.ai.data.Person
import win.rainchan.aishot.aishot.ai.ml.ModelType
import win.rainchan.aishot.aishot.ai.ml.MoveNet
import win.rainchan.aishot.aishot.databinding.ActivityCameraAvtivityBinding
import win.rainchan.aishot.aishot.until.HttpRequestUntil
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraAvtivityBinding
    private var cameraSource: CameraSource? = null
    private var predictImage: Bitmap? = null

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.markBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                val photo = cameraSource?.shot() ?: return@launch
//                 拍照存储
                val tmpFile = File.createTempFile(System.currentTimeMillis().toString(), "png")
                tmpFile.outputStream().use {
                    photo.compress(Bitmap.CompressFormat.JPEG, 80, it)
                }

                val data = synchronized(cameraSource!!.lock) {
                    cameraSource?.detector?.estimateSinglePose(photo)?.toArray() ?: return@launch
                }

                if (predictImage != null) {
                    withContext(Dispatchers.Main) {
                        binding.galleryBtn.setImageResource(android.R.color.white)
                    }
                    predictImage?.recycle()
                    predictImage = null
                }
                val bundle = Bundle()
                bundle.putString("photo", tmpFile.absolutePath)
                bundle.putString("predictData", APP.gson.toJson(data))
                // 显示图片
                withContext(Dispatchers.Main) {
                    binding.galleryBtn.setImageBitmap(predictImage)
                }
                startActivity(
                    Intent(baseContext, PhotoShowActivity::class.java).putExtra(
                        "bundle", bundle
                    )
                )
                photo.recycle()

            }
        }
        binding.btnShot.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val photo = cameraSource?.shot() ?: return@launch
//                Log.d(TAG, getFilesAllName(this@CameraActivity.getExternalFilesDir("data")?.path)[0])
                saveImageToGallery(photo, this@CameraActivity)
                photo.recycle()
            }
        }
        shutHint()
    }

    // 获取当前目录下所有的mp4文件
    private fun getFilesAllName(path: String?): MutableList<String> {
        //传入指定文件夹的路径　　　　
        var file = File(path);
        val files: Array<File> = file.listFiles()
        val imagePaths: MutableList<String> = ArrayList()
        for (i in files.indices) {
            if (checkIsImageFile(files[i].path)) {
                imagePaths.add(files[i].path)
            }
        }
        return imagePaths
    }

    /**
     * 判断是否是照片
     */
    private fun checkIsImageFile(fName: String): Boolean {
        var isImageFile = false
        //获取拓展名
        val fileEnd = fName.substring(
            fName.lastIndexOf(".") + 1,
            fName.length
        ).lowercase(Locale.getDefault())
        isImageFile =
            fileEnd == "jpg" || fileEnd == "png" || fileEnd == "gif" || fileEnd == "jpeg" || fileEnd == "bmp"
        return isImageFile
    }


    @SuppressLint("SimpleDateFormat")
    private fun saveImageToGallery(bmp: Bitmap, context: Context): Int {
        //生成路径

        val appDir = context.getExternalFilesDir("data") ?: return -1
        if (!appDir.exists()) {
            appDir.mkdirs()
            appDir.mkdir()
        }

        //文件名为时间
        val timeStamp = System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sd: String = sdf.format(Date(timeStamp))
        val fileName = "$sd.jpg"

        //获取文件
        val file = File(appDir, fileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            //通知系统相册刷新
            context.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(File(file.path))
                )
            )
            return 2
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return -1
    }

    private fun shutHint() {
        val hint = intent.getBooleanExtra("hint", false)
        if (hint) {
            binding.shotHint.visibility = View.VISIBLE
        }
        // todo 拍照提示模式
    }


    private suspend fun openCamera() {
        cameraSource =
            CameraSource(binding.frameCamera, object : CameraSource.CameraSourceListener {

                var timeSleep = 100

                override fun onFPSListener(fps: Int) {
                    // todo fps
                }

                override fun onDetectedInfo(
                    person: Person?
                ) {
                    // todo 结果
                    if (timeSleep == 0) {
                        // 发送预测结果来获取提示
                        val tips = HttpRequestUntil.getTips(person?.toArray() ?: return)
                        runOnUiThread {
                            binding.shotHint.text = tips.data[0]
                        }
                        timeSleep = 100
                    } else {
                        timeSleep--
                    }

                }

            }).apply {
                prepareCamera()
            }
        withContext(Dispatchers.Main) {
            cameraSource?.initCamera()
        }
        withContext(Dispatchers.IO) {
            HttpRequestUntil.setTips("LqOO5Ko0zSo.png") // 设置要获取拍照提示的图片文件名
        }
        cameraSource?.setDetector(
            MoveNet.create(
                this@CameraActivity,
                Device.GPU,
                ModelType.Thunder
            )
        )

    }


    @ExperimentalSplittiesApi
    private suspend fun requirePermission() {
        ensurePermission(
            permission = Manifest.permission.CAMERA,
            {
                toast("请授权")
                true
            }, true, {
                toast("未授权")
                true
            }, {
                throw IllegalStateException("授权失败")
            })

    }


    override fun onResume() {
        cameraSource?.resume()
        super.onResume()
    }

    override fun onPause() {
        cameraSource?.close()
        super.onPause()
    }

    @ExperimentalSplittiesApi
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                requirePermission()
            }

            openCamera()
        }
    }

}