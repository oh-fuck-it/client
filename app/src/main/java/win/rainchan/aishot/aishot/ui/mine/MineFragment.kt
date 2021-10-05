package win.rainchan.aishot.aishot.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.parse.ParseUser
import win.rainchan.aishot.aishot.activity.LoginActivity
import win.rainchan.aishot.aishot.databinding.FragmentMineBinding


class MineFragment : Fragment() {

    private lateinit var binding: FragmentMineBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        checkLogin()
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        return binding.root
    }


    private fun checkLogin() {
        if (ParseUser.getCurrentUser() != null) {
            binding.loginBtn.visibility = View.GONE
            binding.userText.text = ParseUser.getCurrentUser().username
        }
    }

    override fun onResume() {
        super.onResume()
        checkLogin()
    }

}

