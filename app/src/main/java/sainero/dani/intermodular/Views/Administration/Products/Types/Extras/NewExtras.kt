package sainero.dani.intermodular.Views.Administration.Products.Types.Extras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.ui.theme.IntermodularTheme
import java.lang.NumberFormatException

class NewExtras : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainNewExtra(mainViewModelExtras: MainViewModelExtras) {
    val state = remember { mutableStateOf(0) }

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    var (refresh, onValueChangeRefresh) = rememberSaveable { mutableStateOf(true) }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edición de extras")
                },
                actions = {

                }
            )
        },
        content = {
            if (refresh)  createContent(mainViewModelExtras = mainViewModelExtras, onValueChangeRefresh)
        }
    )
}


@Composable
private fun createContent(mainViewModelExtras: MainViewModelExtras, onValueChangeRefresh: (Boolean) -> Unit) {

Column(
    verticalArrangement = Arrangement.SpaceEvenly
) {

    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp),
    ) {
        mainViewModelExtras._tmpExtras.forEach { extra ->
            item {
                Spacer(modifier = Modifier.padding(5.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    var selectedText by remember { mutableStateOf("") }

                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.Start,

                        ) {
                        OutlinedTextField(
                            value =  selectedText,
                            modifier = Modifier.size(width = 200.dp, height = 55.dp),
                            onValueChange = {
                                selectedText = it
                                extra.name = selectedText
                            },
                            placeholder = { Text(text = "Nombre")},
                        )

                        Spacer(modifier = Modifier.padding(3.dp))

                        //Validar campos
                        var selectedTextPrice by remember { mutableStateOf("") }
                        OutlinedTextField(
                            value =  selectedTextPrice,
                            modifier = Modifier.size(width = 90.dp, height = 55.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                if (it.equals("")) {
                                    selectedTextPrice = it
                                    extra.price = 0f
                                } else {
                                    if (isFloat(it) || it.equals("")) selectedTextPrice = it
                                    extra.price = selectedTextPrice.toFloat()
                                }
                            },
                            placeholder = { Text(text = "0")},
                        )
                        IconButton(
                            onClick = {
                                mainViewModelExtras._tmpExtras.remove(extra)
                                onValueChangeRefresh(false)
                                onValueChangeRefresh(true)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete suggestion",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                        }

                    }

                }
            }
        }
    }

    Spacer(modifier = Modifier.padding(6.dp))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    mainViewModelExtras.addTmpExtras(Extras("",0f))
                    onValueChangeRefresh(false)
                    onValueChangeRefresh(true)
                }
            ) {
                Text(text = "Añadir Extra")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    GlobalVariables.navController.navigate("${Destinations.ProductNewType.route}/cancel")

                }
            ) {
                Text(text = "Revertir cambio")
            }
            Button(
                onClick = {
                    //Guardar cambios
                    onValueChangeRefresh(false)
                    onValueChangeRefresh(true)
                    GlobalVariables.navController.navigate("${Destinations.ProductNewType.route}/edit")
                }
            ) {
                Text(text = "Guardar cambios")
            }
        }
    }
}

}

//Validaciones
private fun isFloat(text: String): Boolean {
    try {
        text.toFloat()
    } catch (e: NumberFormatException) {
        return false
    }
    return true
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview20() {

}