package win.rainchan.aishot.aishot.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import win.rainchan.aishot.aishot.databinding.ActivityPhotoShowBinding

private lateinit var binding: ActivityPhotoShowBinding

class PhotoShowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoShowBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
    }
}

