package win.rainchan.aishot.aishot.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import win.rainchan.aishot.aishot.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private val galleryViewModel: GalleryViewModel by viewModels()
    private lateinit var binding: FragmentGalleryBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        binding.photoList.adapter = PhotoAdapter(this.testData())
        binding.photoList.layoutManager = GridLayoutManager(context, 2)

        return binding.root
    }

    private fun testData() = sequence {
        repeat(100) {
            yield(PhotoListItem("https://pximg.rainchan.win/img?img_id=85472742&web=true"))
        }
    }.toMutableList()
}