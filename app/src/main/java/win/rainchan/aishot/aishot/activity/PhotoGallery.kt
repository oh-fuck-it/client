package win.rainchan.aishot.aishot.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import win.rainchan.aishot.aishot.databinding.ActivityPhotoGalleryBinding
import win.rainchan.aishot.aishot.ui.gallery.GalleryViewModel
import win.rainchan.aishot.aishot.ui.gallery.PhotoAdapter

class PhotoGallery : AppCompatActivity() {
    @ExperimentalStdlibApi
    private val galleryViewModel: GalleryViewModel by viewModels()
    private var adapter = PhotoAdapter(arrayListOf())
    private lateinit var binding: ActivityPhotoGalleryBinding

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoGalleryBinding.inflate(layoutInflater)

        setContentView(binding.root)


        galleryViewModel.dataList.observe(this) {
            if (it.isNotEmpty()) {
                // adapter.setDiffNewData(it.toMutableList())
                adapter.setNewInstance(it.toMutableList())
            }
        }

        galleryViewModel.fetchData()

        binding.recy1.layoutManager = getLayoutManager()
        binding.recy2.layoutManager = getLayoutManager()
        binding.recy3.layoutManager = getLayoutManager()
        binding.recy1.adapter = adapter
        binding.recy2.adapter = adapter
        binding.recy3.adapter = adapter
    }

    private fun getLayoutManager() = LinearLayoutManager(this).apply {
        orientation = LinearLayoutManager.HORIZONTAL
    }
}

