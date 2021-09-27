package win.rainchan.aishot.aishot.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import win.rainchan.aishot.aishot.databinding.FragmentGalleryBinding
import win.rainchan.aishot.aishot.spider.PhotoSpider
import kotlin.coroutines.Continuation

class GalleryFragment : Fragment() {

    @ExperimentalStdlibApi
    private val galleryViewModel: GalleryViewModel by viewModels()
    private lateinit var binding: FragmentGalleryBinding


    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)

        binding.photoList.adapter = PhotoAdapter(galleryViewModel.dataList)
        binding.photoList.layoutManager = GridLayoutManager(context, 2)

        return binding.root
    }

}