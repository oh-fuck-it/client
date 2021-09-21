package win.rainchan.aishot.aishot

import android.app.Application
import com.parse.Parse

class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.APP_ID)
                .clientKey(BuildConfig.APP_KEY)
                .server(BuildConfig.API_URL)
                .enableLocalDataStore()
                .build()
        )
    }
}