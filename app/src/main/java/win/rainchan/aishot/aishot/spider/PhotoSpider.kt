package win.rainchan.aishot.aishot.spider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import win.rainchan.aishot.aishot.APP
import win.rainchan.aishot.aishot.spider.Bean.NewsBean
import win.rainchan.aishot.aishot.spider.Bean.ZxyzGsonClass

object PhotoSpider {
    private const val detailUrl = "http://www.cpanet.cn/detail_picdetail_%s.html"
    private const val hostUrl = "http://www.cpanet.cn"
    private const val url = "http://www.cpanet.cn/detail/zxyzllist"
    private val headers = mapOf(
        "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36",
        "Host" to "www.cpanet.cn",
        "Upgrade-Insecure-Requests" to "1",
        "Referer" to "http://www.cpanet.cn/zxyz.html",
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
    )
    /*
    * @Param: 每页16张图片
    * */
    @ExperimentalStdlibApi
    public suspend fun getPhotos(num:Int):List<NewsBean>{
       return withContext(Dispatchers.IO){
            val index:String = Jsoup.connect(url).headers(headers)
                .data("json","1")
                .data("box","${16*num}")
                .post().body().text()
            val items:ZxyzGsonClass = APP.gson.fromJson(index, ZxyzGsonClass::class.java)

            return@withContext buildList {
                items.forEach {
                    add(NewsBean(title = it.title,imgUrl = "http://www.cpanet.cn"+it.thumb,time = it.createtime))
                }
            }
        }
    }
}