package sainero.dani.intermodular.Views.Administration.Products.Types.Extras

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import sainero.dani.intermodular.ViewModels.ViewModelExtras

class Extras : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainExtras(classLoader: Array<Parcelable>) {
    classLoader.get(0)
    Text(text = "")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview19() {

}