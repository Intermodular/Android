package sainero.dani.intermodular.Views.Administration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.NavigationHost
@ExperimentalFoundationApi

class ProductTypeAdministration : ComponentActivity() {
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
fun MainProductTypeAdministration(navController: NavController) {
    Text(text = "Tipos de producto")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    IntermodularTheme {
        val navController = rememberNavController()
        MainProductTypeAdministration(navController)
    }
}