package win.rainchan.aishot.aishot.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import win.rainchan.aishot.aishot.databinding.FragmentMineBinding


class MineFragment : Fragment() {

    private lateinit var binding: FragmentMineBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding.root

    }
}

