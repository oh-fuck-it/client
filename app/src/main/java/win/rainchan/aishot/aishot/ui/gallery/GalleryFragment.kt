package win.rainchan.aishot.aishot.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import win.rainchan.aishot.aishot.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    @ExperimentalStdlibApi
    private val galleryViewModel: GalleryViewModel by activityViewModels()
    private lateinit var binding: FragmentGalleryBinding
    private  var adapter =PhotoAdapter(arrayListOf())

    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        galleryViewModel.dataList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                // adapter.setDiffNewData(it.toMutableList())
                adapter.setNewData(it.toMutableList())
            }
        }
        galleryViewModel.fetchData()
        binding.photoList.adapter = adapter
        binding.photoList.layoutManager = GridLayoutManager(context, 2)
        return binding.root
    }

}