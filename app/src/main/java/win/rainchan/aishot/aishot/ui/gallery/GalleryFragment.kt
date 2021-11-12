package win.rainchan.aishot.aishot.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import win.rainchan.aishot.aishot.databinding.FragmentGalleryBinding
import java.lang.ref.WeakReference

class GalleryFragment : Fragment() {

    @ExperimentalStdlibApi
    private val galleryViewModel: GalleryViewModel by activityViewModels()
    private lateinit var binding: FragmentGalleryBinding
    private  var adapter =PhotoAdapter(arrayListOf())
    private var mRootView: WeakReference<View>? = null
    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (mRootView!!.get() == null) {
            binding = FragmentGalleryBinding.inflate(inflater, container, false)
            galleryViewModel.dataList.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    adapter.setNewInstance(it.toMutableList())
                }
            }
            galleryViewModel.loadUnsplashData()
            binding.photoList.adapter = adapter
            binding.photoList.layoutManager = GridLayoutManager(context, 2)
            mRootView =  WeakReference<View>(binding.root);
        } else {
            val parent:ViewGroup = mRootView!!.get()!!.parent as ViewGroup;
            with(parent) {
                removeView(mRootView!!.get())
            }
        }
        return mRootView!!.get()!!
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}