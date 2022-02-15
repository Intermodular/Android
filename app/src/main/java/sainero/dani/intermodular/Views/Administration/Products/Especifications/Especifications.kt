package sainero.dani.intermodular.Views.Administration.Products.Especifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.Views.Administration.Products.Types.*

class Especifications : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainEspecifications(id: Int, viewModelProductos: ViewModelProductos, mainViewModelEspecifications: MainViewModelEspecifications) {

    //Consulta BD
    var selectedProduct = remember {viewModelProductos.product}
    var myEspecifications: MutableList<String> = remember { mainViewModelEspecifications._especifications }
    var allEspecifications = remember {
        mutableStateListOf(
            "Vegano",
            "Vegetariano",
            "Picante",
            "Sin gluten",
            "Sin lactosa"
        )
    }


    Surface(color = Color.LightGray) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Especificaciones",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            createEspecifications(
                allEspecifications = allEspecifications,
                suggestions = myEspecifications,
                id = id,
                viewModelProductos = viewModelProductos,
                product = selectedProduct,
                mainViewModelEspecifications = mainViewModelEspecifications)
        }
    }
}








@Composable
private fun createEspecifications(
    allEspecifications: MutableList<String>,
    suggestions: MutableList<String>,
    id: Int,
    viewModelProductos: ViewModelProductos,
    product: Productos,
    mainViewModelEspecifications: MainViewModelEspecifications
) {

    suggestions.forEach{
        allEspecifications.remove(it)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)

    ) {
        allEspecifications.forEach {
            item {
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable {
                            suggestions.add(it)
                            allEspecifications.remove(it)
                        }
                ) {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Yours Especifications",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        suggestions.forEach{

            item{

                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable {
                            allEspecifications.add(it)
                            suggestions.remove(it)
                        }
                ) {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        item {
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {

                Button(
                    onClick = {
                        mainViewModelEspecifications.especificationsState = "Cancel"
                        //mainViewModelEspecifications._especifications = mainViewModelEspecifications._tmpEspecifications

                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Blue
                    ),
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        top = 6.dp,
                        end = 10.dp,
                        bottom = 6.dp
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp, end = 20.dp)
                ) {

                    Text(text = "Cancelar cambios", fontSize = 15.sp)
                }

                Button(
                    onClick = {
                        //Guardar los cambios en la BD

                        mainViewModelEspecifications._tmpEspecifications = suggestions
                        mainViewModelEspecifications.especificationsState = "Edit"
                        navController.popBackStack()

                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Blue
                    ),
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        top = 6.dp,
                        end = 10.dp,
                        bottom = 6.dp
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp, end = 20.dp)
                ) {

                    Text(text = "Guardar cambios", fontSize = 15.sp)
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview16() {
    //MainEspecifications(1,viewModelProductos = ViewModelProductos())
}