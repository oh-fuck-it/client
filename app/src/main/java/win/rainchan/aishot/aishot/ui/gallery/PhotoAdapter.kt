package win.rainchan.aishot.aishot.ui.gallery

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import win.rainchan.aishot.aishot.R
import win.rainchan.aishot.aishot.spider.Bean.NewsBean
import android.view.ViewGroup
import com.bumptech.glide.request.target.Target


class PhotoAdapter(data: MutableList<NewsBean>) :

    BaseQuickAdapter<NewsBean, BaseViewHolder>(R.layout.item_photo_list, data) {
//    @SuppressLint("ResourceType")
//    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        super.onBindViewHolder(holder, position)
//        val img = holder.getView<ImageView>(position)
//        //获取item宽度，计算图片等比例缩放后的高度，为imageView设置参数
//
//        val layoutParams: ViewGroup.LayoutParams = holder.itemView.layoutParams
//        val itemWidth: Float = (ScreenUtils.getScreenWidth(context) - 16 * 3) / 2
//        layoutParams.width = itemWidth.toInt()
//        val scale: Float = (itemWidth + 0f) / img.width
//        layoutParams.height = ((img.height * scale).toInt())
//        img.setLayoutParams(layoutParams)
//        Glide.with(context).load(img.id).override(layoutParams.width, layoutParams.height)
//            .into(img)
//    }

    override fun convert(holder: BaseViewHolder, item: NewsBean) {
        val img = holder.getView<ImageView>(R.id.photo_view)
        Glide.with(holder.itemView).load(item.imgUrl).into(img)

    }

}