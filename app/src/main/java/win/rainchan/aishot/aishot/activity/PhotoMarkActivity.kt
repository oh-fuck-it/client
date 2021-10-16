package win.rainchan.aishot.aishot.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import win.rainchan.aishot.aishot.databinding.ActivityPhotoMarkShowBinding
import java.io.File


class PhotoMarkActivity : ComponentActivity() {
    private lateinit var binding: ActivityPhotoMarkShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoMarkShowBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("bundle")
        val data = bundle?.getString("photo")

        lifecycleScope.launch {
            val score = getScore(data ?: return@launch)
            withContext(Dispatchers.Main) {
                binding.photoMarkScore.text = score.toString()
            }
        }
    }

    suspend fun getScore(imgPath: String): Int {
        val data = withContext(Dispatchers.IO) {
            File(imgPath).inputStream().use {
                it.readBytes()
            }
        }
        // todo 获取评分

        return 100
    }
}
