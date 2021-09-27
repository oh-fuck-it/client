package win.rainchan.aishot.aishot.ui.gallery

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import win.rainchan.aishot.aishot.R
import win.rainchan.aishot.aishot.spider.Bean.NewsBean




class PhotoAdapter(data: MutableList<NewsBean>) :
    BaseQuickAdapter<NewsBean, BaseViewHolder>(R.layout.item_photo_list, data) {

    override fun convert(holder: BaseViewHolder, item: NewsBean) {
        val img = holder.getView<ImageView>(R.id.photo_view)
        Glide.with(holder.itemView).load(item.imgUrl).into(img)

    }

}