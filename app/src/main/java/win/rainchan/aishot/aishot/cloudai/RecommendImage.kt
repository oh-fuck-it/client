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
        val resp = withContext(Dispatchers.IO) { APP.http.newCall(req).execute() }
        val content = resp.body()?.byteStream() ?: throw IllegalArgumentException("失败")
        return BitmapFactory.decodeStream(content)
    }
}