package win.rainchan.aishot.aishot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import win.rainchan.aishot.aishot.databinding.FragmentHomeBinding
import win.rainchan.aishot.aishot.ui.gallery.GalleryViewModel
import win.rainchan.aishot.aishot.ui.gallery.PhotoAdapter
import java.lang.ref.WeakReference

class HomeFragment : Fragment() {
    @ExperimentalStdlibApi
    private val galleryViewModel: GalleryViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private  var adapter =PhotoAdapter(arrayListOf())
    private var mRootView: WeakReference<View>? = null
    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (mRootView == null || mRootView!!.get() == null) {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
            galleryViewModel.dataList.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    adapter.setNewInstance(it.toMutableList())
                }
            }
            galleryViewModel.fetchData()
            binding.homeRecycle.adapter = adapter
            binding.homeRecycle.layoutManager = GridLayoutManager(context, 2)
            mRootView =  WeakReference<View>(binding.root);
        } else {
            val parent:ViewGroup = mRootView!!.get()!!.parent as ViewGroup;
            parent.removeView(mRootView!!.get())
        }
        return mRootView!!.get()!!
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}