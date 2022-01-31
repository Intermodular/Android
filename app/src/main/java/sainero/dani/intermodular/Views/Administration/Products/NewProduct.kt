package sainero.dani.intermodular.Views.Administration.Products

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Views.Administration.Products.ui.theme.IntermodularTheme

@ExperimentalFoundationApi
class NewProduct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@Composable
fun MainNewProduct() {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }

    //Textos
    var (textName, onValueChangeName) = rememberSaveable{ mutableStateOf("") }
    var (textTipe, onValueChangeTipe) = rememberSaveable{ mutableStateOf("") }
    var (textIngredients, onValueChangeIngredients) = rememberSaveable{ mutableStateOf("") }
    var (textCost, onValueChangeCost) = rememberSaveable{ mutableStateOf("") }
    var (textEspecification, onValueChangeEspecification) = rememberSaveable{ mutableStateOf("") }
    var (textImg, onValueChangeImg) = rememberSaveable{ mutableStateOf("") }
    var (textStock, onValueChangeStock) = rememberSaveable{ mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nuevo producto",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {


                }
            )

        },
        content = {
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            ) {
                createRowList("Nombre",textName,onValueChangeName)
                createRowList("Tipo",textTipe,onValueChangeTipe)
                createRowList("Ingredientes",textIngredients,onValueChangeIngredients)
                createRowList("Coste",textCost,onValueChangeCost)
                createRowList("Especificación",textEspecification,onValueChangeEspecification)
                createRowList("Imágen",textImg,onValueChangeImg)
                createRowList("Stock",textStock,onValueChangeStock)
                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {

                            textName = ""
                            textTipe = ""
                            textIngredients = ""
                            textCost = ""
                            textEspecification = ""
                            textImg = ""
                            textStock = ""

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
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Resetear campos",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Resetear campos", fontSize = 15.sp)
                    }



                    Button(
                        onClick = {
                            //Guardar los cambios en la BD

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
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Crear producto",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Crear producto", fontSize = 15.sp)
                    }

                }
            }

        }
    )
}

@Composable
private fun createRowList(text: String, value: String, onValueChange: (String) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(text) },
            label = { Text(text = text) },
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview12() {
    IntermodularTheme {
        MainNewProduct()
    }
}