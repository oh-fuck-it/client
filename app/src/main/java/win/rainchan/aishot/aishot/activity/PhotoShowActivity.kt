package win.rainchan.aishot.aishot.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import win.rainchan.aishot.aishot.cloudai.RecommendImage
import win.rainchan.aishot.aishot.databinding.ActivityPhotoShowBinding
import java.io.File

private lateinit var binding: ActivityPhotoShowBinding

class PhotoShowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoShowBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        val bundle = intent.getBundleExtra("bundle")
        val data = bundle?.getString("photo")
        val imgData = bundle?.getString("predictData")
        if (data != null) {
            Glide.with(binding.root).load(File(data)).into(binding.photoView)
        }
        lifecycleScope.launch{
            val predictImg = RecommendImage.getPredictImage(imgData!!)
            withContext(Dispatchers.Main){
                Glide.with(binding.root).load(predictImg).into(binding.galleryBtn)
            }
        }


    }
}

