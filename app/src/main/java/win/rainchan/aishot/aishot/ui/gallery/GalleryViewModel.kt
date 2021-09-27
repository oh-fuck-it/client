package win.rainchan.aishot.aishot.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import win.rainchan.aishot.aishot.spider.Bean.NewsBean
import win.rainchan.aishot.aishot.spider.PhotoSpider
import java.util.*

@ExperimentalStdlibApi
class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val dataList :MutableList<NewsBean> = ArrayList()
    val text: LiveData<String> = _text
    init {
        loadData()
    }
    @ExperimentalStdlibApi
    fun loadData(){
        viewModelScope.launch(Dispatchers.IO) {
            PhotoSpider.getNews(1).forEach {
                it?.let { it1 -> dataList.add(it1) }
            }
        }
    }
}