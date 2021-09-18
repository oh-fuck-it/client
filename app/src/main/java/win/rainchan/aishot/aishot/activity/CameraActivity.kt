package win.rainchan.aishot.aishot.activity

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import splitties.experimental.ExperimentalSplittiesApi
import splitties.permissions.ensurePermission
import splitties.toast.toast
import win.rainchan.aishot.aishot.R
import win.rainchan.aishot.aishot.databinding.ActivityCameraAvtivityBinding
import java.lang.IllegalStateException

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraAvtivityBinding
    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            requirePermission()
        }
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

}