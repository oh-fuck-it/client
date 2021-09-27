package win.rainchan.aishot.aishot.ui.gallery

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import win.rainchan.aishot.aishot.R


data class PhotoListItem(val url: String) {}


class PhotoAdapter(data: MutableList<PhotoListItem>) :
    BaseQuickAdapter<PhotoListItem, BaseViewHolder>(R.layout.item_photo_list, data) {

    override fun convert(holder: BaseViewHolder, item: PhotoListItem) {
        val img = holder.getView<ImageView>(R.id.photo_view)
        Glide.with(holder.itemView).load(item.url).into(img)

    }

}