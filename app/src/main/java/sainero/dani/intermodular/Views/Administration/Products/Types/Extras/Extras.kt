package sainero.dani.intermodular.Views.Administration.Products.Types.Extras

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelExtras
import java.io.IOException
import java.lang.Float.*
import java.lang.NumberFormatException
import java.util.regex.Pattern

@Composable
fun MainExtras(mainViewModelExtras: MainViewModelExtras) {
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
private fun createContent(
    mainViewModelExtras: MainViewModelExtras,
    onValueChangeRefresh: (Boolean) -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {

        mainViewModelExtras._tmpExtras.forEach { extra ->
            item {
                Spacer(modifier = Modifier.padding(5.dp))

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    var selectedText by remember { mutableStateOf(extra.name) }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value =  selectedText,
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(start = 10.dp, end = 10.dp),
                            onValueChange = {
                                selectedText = it
                                extra.name = selectedText
                            },
                            placeholder = { Text(text = extra.name)},
                            label = { Text(text = "Extra")},
                        )

                        Spacer(modifier = Modifier.padding(3.dp))

                        var selectedTextPrice by remember { mutableStateOf(extra.price.toString()) }

                        OutlinedTextField(
                            value =  selectedTextPrice,
                            modifier = Modifier
                                .fillMaxWidth(0.7f),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                if (it.equals("")) {
                                    selectedTextPrice = it
                                    extra.price = 0f
                                } else {
                                    if (mainViewModelExtras.isFloat(it)) {
                                        selectedTextPrice = it
                                        extra.price = selectedTextPrice.toFloat()
                                    }
                                }
                            },
                            placeholder = { Text(text = "Precio")},
                            label = { Text(text = "Precio")},
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
        item {
            Spacer(modifier = Modifier.padding(6.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, end = 10.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, end = 10.dp)
                ) {
                    Button(
                        onClick = {
                            mainViewModelExtras.extrasState = "Cancel"
                            navController.popBackStack()
                        },
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            top = 6.dp,
                            end = 10.dp,
                            bottom = 6.dp
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)
                    ) {
                        Text(text = "Revertir cambios")
                    }
                    Button(
                        onClick = {
                            //Guardar cambios
                            onValueChangeRefresh(false)
                            onValueChangeRefresh(true)
                            mainViewModelExtras.extrasState = "Edit"
                            navController.popBackStack()
                        },
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            top = 6.dp,
                            end = 10.dp,
                            bottom = 6.dp
                        ),
                    ) {
                        Text(text = "Guardar cambios")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview19() {

}