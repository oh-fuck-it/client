package win.rainchan.aishot.aishot.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import win.rainchan.aishot.aishot.databinding.FragmentGalleryBinding
import win.rainchan.aishot.aishot.spider.Bean.NewsBean

class GalleryFragment : Fragment() {

    @ExperimentalStdlibApi
    private val galleryViewModel: GalleryViewModel by activityViewModels()
    private lateinit var binding: FragmentGalleryBinding
    private var adapter = PhotoAdapter(arrayListOf())

    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        galleryViewModel.dataList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setNewInstance(it.toMutableList())
            }
        }
        galleryViewModel.loadUnsplashData()
        binding.photoList.adapter = adapter
        adapter.setNewInstance(galleryViewModel.dataList.value as MutableList<NewsBean>?)
        binding.photoList.layoutManager = GridLayoutManager(context, 2)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}