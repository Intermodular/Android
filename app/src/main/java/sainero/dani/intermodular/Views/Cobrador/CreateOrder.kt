package sainero.dani.intermodular.Views.Cobrador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.NavigationHost

class CreateOrder : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {
                NavigationHost()
            }
        }
    }
}

@Composable
fun MainCreateOrder(navController: NavController, idTable: String) {
    //Realizar la busqueda a la BD usando el idTable

    Text(text = "${idTable}")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    IntermodularTheme {
        val navController = rememberNavController()
        MainCreateOrder(navController,"1")
    }
}