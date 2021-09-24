package win.rainchan.aishot.aishot

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseUser
import okhttp3.OkHttpClient
import win.rainchan.aishot.aishot.entity.Post

class APP : Application() {

    companion object {
        val SESSION_KEY = "session"
        lateinit var ctx: APP
        val http: OkHttpClient = OkHttpClient.Builder().build()
        val gson = Gson()
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
        ParseObject.registerSubclass(Post::class.java)
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.APP_ID)
                .clientKey(BuildConfig.APP_KEY)
                .server(BuildConfig.API_URL)
                .enableLocalDataStore()
                .build()
        )

        autoLogin()
    }

    private fun autoLogin() {
        if (ParseUser.getCurrentUser() == null) {
            val key =
                getSharedPreferences("auth", Context.MODE_PRIVATE).getString(SESSION_KEY, null)
            if (key != null) {
//                ParseUser.becomeInBackground(key) { user, e ->
//                    if (e!= null){
//                        toast("会话已过期 $e")
//                    }
//                }
                ParseUser.become(key)
            }
        }
    }

    fun login(username: String, pwd: String) {
        val user = ParseUser.logIn(username, pwd)
        getSharedPreferences("auth", Context.MODE_PRIVATE).edit()
            .putString(SESSION_KEY, user.sessionToken).apply()

    }

}
