package sainero.dani.intermodular.Views.Administration.Zone.Table

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Zonas
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.ViewModels.ViewModelZonas
import sainero.dani.intermodular.Views.Administration.Zone.Table.ui.theme.IntermodularTheme

class EditTable : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainEditTable(_id: Int,viewModelMesas: ViewModelMesas) {

    viewModelMesas.getMesaById(_id)
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    var deleteUser = remember { mutableStateOf(false)}

    if (deleteUser.value){
        confirmDeleteTable(viewModelMesas = viewModelMesas,id = _id)
    }

    //Esto se eliminará por una consulta a la BD
    var selectedTable : Mesas = Mesas(_id,"Table${_id}",2,"libre",3)

    viewModelMesas.mesasListResponse.forEach{
        if (it._id.equals(_id)) selectedTable = it
    }

    //Textos
    var (textZone, onValueChangeZone) = rememberSaveable{ mutableStateOf(selectedTable.zone) }
    var (textNºsillas, onValueChangeNºsillas) = rememberSaveable{ mutableStateOf(selectedTable.numChair.toString()) }
    var (textState, onValueChangeState) = rememberSaveable{ mutableStateOf(selectedTable.state)}
    var (textNumber, onValueChangeNumber) = rememberSaveable{ mutableStateOf(selectedTable.number.toString())}

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edición de Mesa",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {
                    IconButton(onClick = {
                        deleteUser.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Icon",
                            tint = Color.White
                        )
                    }

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

                createRowList(text = "Nombre", value = textZone, onValueChange = onValueChangeZone,false, true)
                createRowList(text = "NºSillas", value = textNºsillas, onValueChange = onValueChangeNºsillas,true, true)
                createRowList(text = "Estado", value = textState, onValueChange = onValueChangeState, false,true)
                createRowList(text = "Numero", value = textNumber, onValueChange = onValueChangeNumber, true,true)

                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            textZone = ""
                            textNºsillas = ""
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
                            contentDescription = "Revertir cambios",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Revertir cambios", fontSize = 15.sp)
                    }



                    Button(
                        onClick = {
                            selectedTable.zone = textZone
                            selectedTable.numChair = textNºsillas.toInt()
                            selectedTable.state = textState
                            selectedTable.number = textNumber.toInt()

                            //Guardar zona  en la BB


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
                            contentDescription = "Guardar cambios",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Guardar cambios", fontSize = 15.sp)
                    }

                }
            }

        }
    )
}


@Composable
fun createRowList(text: String, value: String, onValueChange: (String) -> Unit, numeric : Boolean, enable: Boolean) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            enabled = enable,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(text) },
            label = { Text(text = text) },
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = if (numeric) KeyboardType.Number else KeyboardType.Text)
        )

    }
}

@Composable
private fun confirmDeleteTable(viewModelMesas: ViewModelMesas, id: Int) {
    MaterialTheme {

        Column {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "¿Seguro que desea eliminar la mesa seleccionada?")
                },
                text = {
                    Text("No podrás volver a recuperarla")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModelMesas.deleteMesa(id)
                            GlobalVariables.navController.navigate(Destinations.TableManager.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        ),
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            GlobalVariables.navController.navigate("${Destinations.EditTable.route}/${id}")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview17() {

}