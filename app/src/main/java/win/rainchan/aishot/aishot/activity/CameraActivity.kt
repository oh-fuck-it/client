package win.rainchan.aishot.aishot.activity

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
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
import win.rainchan.aishot.aishot.cloudai.RecommendImage
import win.rainchan.aishot.aishot.databinding.ActivityCameraAvtivityBinding
import java.io.File


class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraAvtivityBinding
    private var cameraSource: CameraSource? = null
    private var predictImage: Bitmap? = null

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnShot.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                val photo = cameraSource?.shot() ?: return@launch
//                 拍照存储
                val tmpFile = File.createTempFile(System.currentTimeMillis().toString(), "png")
                tmpFile.outputStream().use {
                    photo.compress(Bitmap.CompressFormat.JPEG, 80, it)
                }

                val data =  synchronized(cameraSource!!.lock){
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
                        "bundle",bundle
                    )
                )
                photo.recycle()

            }
        }
        binding.markBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                //TODO: 这里写评分逻辑
            }
        }
    }


    private suspend fun openCamera() {
        cameraSource =
            CameraSource(binding.frameCamera, object : CameraSource.CameraSourceListener {
                override fun onFPSListener(fps: Int) {
                    // todo fps
                }

                override fun onDetectedInfo(
                    personScore: Float?,
                    poseLabels: List<Pair<String, Float>>?
                ) {
                    // todo 结果

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