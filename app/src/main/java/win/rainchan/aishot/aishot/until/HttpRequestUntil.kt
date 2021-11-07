package win.rainchan.aishot.aishot.until

import com.google.gson.Gson
import okhttp3.*
import org.jsoup.internal.StringUtil
import win.rainchan.aishot.aishot.spider.Bean.SetTipsResult
import java.util.concurrent.TimeUnit

object HttpRequestUntil {
    fun setTips(imgName:String): SetTipsResult {
        return request("POST","http://62.234.132.110:5000/setTips", mapOf(
            "img" to imgName
        ))
    }
    fun getTips(predJoints:ArrayList<ArrayList<Double>>){
        val arrayString:String = predJoints.joinToString(
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
        return request("GET", "http://62.234.132.110:5000/getTips", mapOf(
            "pred_joints" to arrayString
        ))
    }
    fun initClient(): OkHttpClient {
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
    inline fun <reified T> request(method:String,url:String, header:Map<String,String>?=null):T{
       val client = initClient()
        val formBodyBuilder = FormBody.Builder()
        header?.forEach{
            formBodyBuilder.add(
                it.key, it.value
            )
        }
        val formBody = formBodyBuilder.build()
        val requestBody = Request.Builder()
            .url(url)
            .method(method,formBody)
            .build()
        val responseBody = client.newCall(requestBody)
            .execute().body()?.string()
        return  Gson().fromJson(responseBody,T::class.java);
    }

}