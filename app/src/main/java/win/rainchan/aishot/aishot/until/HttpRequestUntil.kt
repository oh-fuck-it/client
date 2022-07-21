package win.rainchan.aishot.aishot.until

import com.google.gson.Gson
import okhttp3.*
import win.rainchan.aishot.aishot.spider.Bean.MarkDataClass
import win.rainchan.aishot.aishot.spider.Bean.SetTipsResult
import win.rainchan.aishot.aishot.spider.Bean.TipsResult
import java.io.File
import java.util.concurrent.TimeUnit


object HttpRequestUntil {
    fun postMarker(file: File): MarkDataClass? {
        return requestFile("POST", "http://101.34.24.60:5000/markerImg", file)
    }
    fun setTips(imgName:String): SetTipsResult? {
        return request(
            "POST", "http://101.34.24.60:5000/setTips", mapOf(
                "img" to imgName
            )
        )
    }

    fun getTips(predJoints: Array<Array<Float>>): TipsResult? {
        if (predJoints.size <=1) return null
        val arrayString: String = predJoints.joinToString(
            prefix = "[",
            separator = ",",
            postfix = "]",
            transform = {
                it.joinToString(
                    prefix = "[",
                    separator = ",",
                    postfix = "]",
                    transform = { number ->
                        number.toString()
                    }
                )
            }
        )
        return request(
            "POST", "http://101.34.24.60:5000/getTips", mapOf(
                "pred_joints" to arrayString
            )
        )
    }
    private fun initClient(): OkHttpClient {
        var client= OkHttpClient()
        val READ_TIMEOUT = 100;
        val CONNECT_TIMEOUT = 60;
        val WRITE_TIMEOUT = 60;
        val builder = client.newBuilder()
        builder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        //连接超时
        builder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS);
        //写入超时
        builder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS);
        builder.connectionPool(ConnectionPool(32, 5, TimeUnit.MINUTES))
        client = builder.build()
        return client
    }
    /*
    * @param: method:GET、POST
    * */
    private inline fun <reified T> request(method:String, url:String, header:Map<String,String>?=null): T? {
       val client = initClient()
        val formBodyBuilder = FormBody.Builder()
        header?.forEach{
            formBodyBuilder.add(
                it.key, it.value
            )
        }
        val formBody = formBodyBuilder.build()
        val request = Request.Builder()
            .url(url)
            .method(method,formBody)
            .build()
        val responseBody = client.newCall(request)
            .execute().body()?.string()
        return try{
            Gson().fromJson(responseBody,T::class.java);
        }catch(e:Exception){
            null
        }
    }
    private inline fun <reified T> requestFile(method:String, url:String, file: File):T?{
        val client = initClient()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", file.name,
                RequestBody.create(MediaType.parse("image/png"), file)
            )
            .build()
        val request = Request.Builder()
            .url(url)
            .method(method,requestBody)
            .build()
        val responseBody = client.newCall(request)
            .execute().body()?.string()
        return try {
            Gson().fromJson(responseBody,T::class.java);
        }catch(e:Exception){
            null
        }
    }
}