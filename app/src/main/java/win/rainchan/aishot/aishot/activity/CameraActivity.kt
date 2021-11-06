package win.rainchan.aishot.aishot.activity

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import win.rainchan.aishot.aishot.ai.ml.ModelType
import win.rainchan.aishot.aishot.ai.ml.MoveNet
import win.rainchan.aishot.aishot.databinding.ActivityCameraAvtivityBinding
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


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
//                 拍照存储
                saveImg(photo, System.currentTimeMillis().toString(), this@CameraActivity)
                photo.recycle()
            }
        }
        shutHint()
    }


    private fun saveImg(bitmap: Bitmap, name: String, context: Context): Boolean {
        try {
            var sdcardPath = System.getenv("EXTERNAL_STORAGE");      //获得sd卡路径
            var dir = "$sdcardPath/ai_shot/";                    //图片保存的文件夹名
            var file = File(dir);                                 //已File来构建
            if (!file.exists()) {                                     //如果不存在  就mkdirs()创建此文件夹
                file.mkdirs();
            }
            var mFile = File(dir + name);                        //将要保存的图片文件
            if (mFile.exists()) {
                Toast.makeText(context, "该图片已存在!", Toast.LENGTH_SHORT).show();
                return false;
            }

            var outputStream = FileOutputStream(mFile);     //构建输出流
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                outputStream
            );  //compress到输出outputStream
            var uri = Uri.fromFile(mFile);                                  //获得图片的uri
            context.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    uri
                )
            ); //发送广播通知更新图库，这样系统图库可以找到这张图片
            return true;
        } catch (e: Exception) {
            Log.d(TAG, "saveImg: 111111111111111111111111111111111111111111111")
            e.printStackTrace()
        };
        return false;
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
                    personScore: Float?,
                    poseLabels: List<Pair<String, Float>>?
                ) {
                    // todo 结果
                    if (timeSleep == 0) {
                        // 发送预测结果来获取提示


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