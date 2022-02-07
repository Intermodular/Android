package sainero.dani.intermodular.Views.Cobrador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.ViewModels.ViewModelTipos

class CreateOrder : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainCreateOrder(id: Int, viewModelProductos: ViewModelProductos, viewModelTipos: ViewModelTipos) {

    //Busquedas BD
    val allTypes = viewModelTipos.typeListResponse
    var allProducts = viewModelProductos.productListResponse
    var allTypesNames: MutableList<String> = mutableListOf()
    allTypes.forEach { allTypesNames.add(it.name) }

    //Varaibles de ayuda
    var state = remember { mutableStateOf(0)}
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var nameOfSelectedType = remember { mutableStateOf(allTypesNames[0])}

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            //Crear filtro
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                ScrollableTabRow(
                    selectedTabIndex = state.value,
                    divider = {
                        /* Divider(
                             modifier = Modifier
                                 .height(8.dp)
                                 .fillMaxWidth()
                                 .background(color = Color.Blue)
                         )*/
                    },
                    modifier = Modifier.wrapContentWidth(),
                    edgePadding = 16.dp,
                ) {
                    allTypesNames.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = state.value == index,
                            onClick = {
                                state.value = index
                                nameOfSelectedType.value = title
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                createProducts(allProducts,nameOfSelectedType.value)
            }

        }
    )

}



@ExperimentalFoundationApi
@Composable
private fun createProducts(allProducts: List<Productos>, nameOfSelectedType: String) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp),

    ) {
        for (i in allProducts) {
            if (i.type.equals(nameOfSelectedType)){


                item {
                    Card(
                        backgroundColor = Color.Red,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .width(50.dp)
                            .height(50.dp),
                        elevation = 8.dp,

                    ) {
                        Button(
                            onClick = {

                            },
                            modifier = Modifier.pointerInput(Unit){
                                detectTapGestures (
                                    onLongPress = {
                                        //Evnto al mantener
                                    }
                                )
                            },
                            contentPadding = PaddingValues(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Blue,
                                contentColor = Color.White
                            )
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = i.name,
                                    fontSize = 10.sp,
                                    modifier = Modifier.fillMaxSize(),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.padding(4.dp))
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    IntermodularTheme {
        //MainCreateOrder(1)
    }
}