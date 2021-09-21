package win.rainchan.aishot.aishot

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.parse.ParseUser
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserTest {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val userName =
        buildString { repeat(10) { append("536748941321320213637gfvdsyiuhgw34udsjhgvcnhsjxvgflkuj".random()) } }

    @Before
    fun registerTest() {
        // Context of the app under test.
        val user = ParseUser().apply {
            username = userName
            email = "$userName@test.com"
            setPassword("gfdoigiudio")
        }
        user.signUp()
    }

    @Test
    fun loginTest() {
        ParseUser.logIn("$userName@test.com", "gfdoigiudio")
    }

    @Test
    fun logoutTest() {
        ParseUser.logOut()
    }
}