package win.rainchan.aishot.aishot.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import win.rainchan.aishot.aishot.spider.Bean.NewsBean
import win.rainchan.aishot.aishot.spider.PhotoSpider

@ExperimentalStdlibApi
class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val dataList :MutableLiveData<List<NewsBean>> = MutableLiveData()
    val text: LiveData<String> = _text
    @ExperimentalStdlibApi
    fun loadData(){
        viewModelScope.launch(Dispatchers.Main) {

            dataList.value =    PhotoSpider.getPhotos(1)
        }
    }
}