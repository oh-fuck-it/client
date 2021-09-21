package win.rainchan.aishot.aishot.entity

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.ktx.delegates.intAttribute
import com.parse.ktx.delegates.listAttribute
import com.parse.ktx.delegates.relationAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("Post")
class Post :ParseObject() {
    var content by stringAttribute()
    var images by listAttribute<ParseFile>()
    var like by intAttribute()
}