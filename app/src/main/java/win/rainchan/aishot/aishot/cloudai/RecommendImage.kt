package win.rainchan.aishot.aishot.cloudai

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
import win.rainchan.aishot.aishot.APP
import win.rainchan.aishot.aishot.BuildConfig

object RecommendImage {
    suspend fun getImage(vector: String): Bitmap {
        val req = Request.Builder().url("${BuildConfig.PREDCT_API}/predict")
            .post(
                FormBody.Builder().add("img", vector).build()
            ).build()
        return withContext(Dispatchers.IO) {
            val resp = APP.http.newCall(req).execute()

            val content = resp.body()?.bytes() ?: throw IllegalArgumentException("失败")

            BitmapFactory.decodeByteArray(content, 0, content.size)
        }

    }
}