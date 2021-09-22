package win.rainchan.aishot.aishot.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import win.rainchan.aishot.aishot.R


class PhotoShowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Column() {
                    TopAppBar() {

                    }
                    content()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun content() {
    Column(Modifier.padding(8.dp)) {
        Card {
            Image(

                imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu_gallery),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "这里是一些操作")
            }

        }
    }
}