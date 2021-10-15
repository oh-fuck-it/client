package win.rainchan.aishot.aishot.cloudai

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import win.rainchan.aishot.aishot.APP
import win.rainchan.aishot.aishot.BuildConfig
import java.io.*
import java.lang.Exception
import java.nio.ByteBuffer
import win.rainchan.aishot.aishot.spider.Bean.MarkDataClass


object RecommendImage {
    suspend fun getPredictImage(vector: String): Bitmap {
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
    fun bytesToFile(bytes: ByteArray?, outPath: String, fileName: String): File? {
        var bos: BufferedOutputStream? = null
        var fos: FileOutputStream? = null
        var file: File? = null
        try {
            val dir = File(outPath)
            if (!dir.exists() && dir.isDirectory) { //判断文件目录是否存在
                dir.mkdirs()
            }
            file = File(outPath + File.separator + fileName)
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)
            bos.write(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (bos != null) {
                try {
                    bos.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
        }
        return file
    }

    suspend fun getImageMark(img: Bitmap):MarkDataClass{
        val buf = ByteBuffer.allocate(img.byteCount)
        img.copyPixelsToBuffer(buf)
        val byteArr = buf.array()
        val file = bytesToFile(byteArr,"./File","img")!!
        val req = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", "img",
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            )
            .build()
        val response = Request.Builder().url("${BuildConfig.PREDCT_API}/markerImg")
            .post(
                req
            ).build()
        return withContext(Dispatchers.IO) {
            val resp = APP.http.newCall(response).execute()
            val content: ResponseBody = resp.body()?: throw IllegalArgumentException("失败")
            return@withContext APP.gson.fromJson(content.string(),MarkDataClass::class.java)
        }
    }
}
