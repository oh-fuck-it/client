package win.rainchan.aishot.aishot.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import win.rainchan.aishot.aishot.APP
import win.rainchan.aishot.aishot.R
import win.rainchan.aishot.aishot.databinding.ActivityPhotoGalleryBinding
import win.rainchan.aishot.aishot.ui.gallery.GalleryViewModel
import win.rainchan.aishot.aishot.ui.gallery.PhotoAdapter

class PhotoGallery : AppCompatActivity() {
    @ExperimentalStdlibApi
    private val galleryViewModel: GalleryViewModel by viewModels()
    private val localImgViewModel: LocalImgViewModel by viewModels()
    private var adapter = PhotoAdapter(arrayListOf())
    private var localadapter = LocalPhotoAdapter(arrayListOf())
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


        localImgViewModel.imgList.observe(this) {
            if (it.isNotEmpty()) {
                // adapter.setDiffNewData(it.toMutableList())
                localadapter.setNewInstance(it.toMutableList())
            }
        }


        galleryViewModel.fetchData()
        localImgViewModel.loadData()

        binding.recy1.layoutManager = getLayoutManager()
        binding.recy2.layoutManager = getLayoutManager()
        binding.recy3.layoutManager = getLayoutManager()
        binding.recy1.adapter = localadapter
        binding.recy2.adapter = adapter
        binding.recy3.adapter = adapter
    }

    private fun getLayoutManager() = LinearLayoutManager(this).apply {
        orientation = LinearLayoutManager.HORIZONTAL
    }
}

class LocalImgViewModel() : ViewModel() {
    val imgList = MutableLiveData<List<String>>()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val fileList = APP.ctx.getExternalFilesDir("data")?.listFiles()
                ?.filter { it.name.endsWith(".jpg") }?.map { it.absolutePath } ?: return@launch
            withContext(Dispatchers.Main) {
                imgList.value = fileList
            }
        }
    }
}


class LocalPhotoAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_photo_list, data) {
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ResourceType")
    override fun convert(holder: BaseViewHolder, item: String) {
        val img = holder.getView<ImageView>(R.id.photo_view)
        Glide.with(holder.itemView).load(item).into(img)

    }

}