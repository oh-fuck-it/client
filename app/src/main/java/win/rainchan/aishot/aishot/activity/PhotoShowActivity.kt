package win.rainchan.aishot.aishot.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import win.rainchan.aishot.aishot.databinding.ActivityPhotoShowBinding
import java.io.File

private lateinit var binding: ActivityPhotoShowBinding

class PhotoShowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoShowBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        val data = intent.getStringExtra("photo")
        if (data != null) {
            Glide.with(binding.root).load(File(data)).into(binding.photoView)
        }

    }
}

