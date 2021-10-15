package win.rainchan.aishot.aishot.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import win.rainchan.aishot.aishot.databinding.ActivityPhotoMarkShowBinding

private lateinit var binding: ActivityPhotoMarkShowBinding
class PhotoMarkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoMarkShowBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

    }
}
