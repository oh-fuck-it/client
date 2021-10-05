package win.rainchan.aishot.aishot.ui.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import win.rainchan.aishot.aishot.R
import win.rainchan.aishot.aishot.spider.Bean.NewsBean
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.request.target.Target
import splitties.systemservices.windowManager
import android.view.WindowManager
import androidx.cardview.widget.CardView


class PhotoAdapter(data: MutableList<NewsBean>) :

    BaseQuickAdapter<NewsBean, BaseViewHolder>(R.layout.item_photo_list, data) {
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ResourceType")
    override fun convert(holder: BaseViewHolder, item: NewsBean) {
        val img = holder.getView<ImageView>(R.id.photo_view)
        Glide.with(holder.itemView).load(item.imgUrl).into(img)
        
    }

}