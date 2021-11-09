package win.rainchan.aishot.aishot.ui.society

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import win.rainchan.aishot.aishot.activity.PostViewActivity
import win.rainchan.aishot.aishot.databinding.FragmentSocietyBinding

class SocietyFragment : Fragment() {
    private lateinit var binding: FragmentSocietyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSocietyBinding.inflate(inflater, container, false)
        binding.fakeImg.setOnClickListener {
            startActivity(Intent(requireContext(), PostViewActivity::class.java))
        }
        return binding.root
    }

}