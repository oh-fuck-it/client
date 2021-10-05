package win.rainchan.aishot.aishot.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import win.rainchan.aishot.aishot.activity.CameraActivity
import win.rainchan.aishot.aishot.databinding.FragmentHomeBinding
import win.rainchan.aishot.aishot.ui.gallery.GalleryViewModel
import win.rainchan.aishot.aishot.ui.gallery.PhotoAdapter

class HomeFragment : Fragment() {
    @ExperimentalStdlibApi
    private val galleryViewModel: GalleryViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private  var adapter =PhotoAdapter(arrayListOf())

    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        galleryViewModel.dataList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setNewData(it.toMutableList())
            }
        }
        binding.button.setOnClickListener {
            startActivity(Intent(requireContext(), CameraActivity::class.java))
        }
        galleryViewModel.fetchData()
        binding.homeRecycle.adapter = adapter
        binding.homeRecycle.layoutManager = GridLayoutManager(context, 2)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}