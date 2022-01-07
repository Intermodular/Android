package sainero.dani.intermodular.Views.Administration.Products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.navigation.Destinations
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

@ExperimentalFoundationApi
@Composable
fun MainProductTypeAdministration(navController: NavController) {


}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    IntermodularTheme {
        val navController = rememberNavController()
        MainProductTypeAdministration(navController)
    }
}

