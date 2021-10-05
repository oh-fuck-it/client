package win.rainchan.aishot.aishot.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.parse.ParseException
import com.parse.ParseUser
import com.parse.coroutines.suspendSignUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.toast.toast
import win.rainchan.aishot.aishot.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.login.setOnClickListener {
            loginOrRegister()
        }

        setContentView(binding.root)
    }

    private fun loginOrRegister() {
        val username = binding.username.text
        val password = binding.password.text
        val email = binding.email.text
        if (username.isEmpty()) {
            toast("用户名不能为空")
            return
        }
        if (email.isEmpty()) {
            toast("邮箱不能为空")
            return
        }
        if (password.isEmpty()) {
            toast("密码不能为空")
            return
        }
        binding.login.isEnabled = false
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {

            try {
                ParseUser.logIn(username.toString(), password.toString())

            } catch (e: ParseException) {
                ParseUser().apply {
                    setUsername(username.toString())
                    setPassword(password.toString())
                    setEmail(email.toString())

                }.suspendSignUp()
            }
            withContext(Dispatchers.Main) {
                finish()
            }
        }
    }

}