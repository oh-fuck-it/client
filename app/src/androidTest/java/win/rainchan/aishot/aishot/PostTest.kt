package win.rainchan.aishot.aishot

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.parse.ParseUser
import com.parse.coroutines.suspendSave
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import win.rainchan.aishot.aishot.entity.Post


@RunWith(AndroidJUnit4::class)
class PostTest {
    @Test
    fun testAddPost(){
        println(ParseUser.getCurrentUser().email)
        runBlocking {
            val post = Post().apply {
                this.content = "lalalaa"

                this.like = 1

            }
            post.suspendSave()
            ParseUser.getCurrentUser().getRelation<Post>("posts").add(post)
            ParseUser.getCurrentUser().save()
        }

    }
}