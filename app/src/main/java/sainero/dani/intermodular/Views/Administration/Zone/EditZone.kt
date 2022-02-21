package sainero.dani.intermodular.Views.Administration.Zone

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Tipos
import sainero.dani.intermodular.DataClass.Zonas
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.ViewModels.ViewModelZonas
import sainero.dani.intermodular.ViewsItems.confirmAlertDialog
import sainero.dani.intermodular.ViewsItems.createRowList
import sainero.dani.intermodular.ViewsItems.createRowListWithErrorMesaje

import java.util.regex.Pattern


@Composable
fun MainEditZone(
    _id: Int,
    mainViewModelZone:MainViewModelZone
) {

    //Variables de ayuda
    val showToast = remember { mutableStateOf(false) }
    val textOfToast = remember { mutableStateOf("") }
    mainViewModelZone.getZoneById(_id)
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var (deleteItem,onValueChangeDeleteItem) = remember { mutableStateOf(false)}
    var context = LocalContext.current

    var selectedZone : Zonas = Zonas(_id,"Zone${_id}",2)
    mainViewModelZone.zonesListResponse.forEach{
        if (it._id.equals(_id)) selectedZone = it
    }

    if (showToast.value) {
        Toast.makeText(LocalContext.current,textOfToast.value, Toast.LENGTH_SHORT).show()
        showToast.value = false
    }

    if (deleteItem) {
        var title: String = "¿Seguro que desea eliminar la zona seleccionada?"
        var subtitle: String = "No podrás volver a recuperarla"

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteItem,
        ) {
            if (it) {
                mainViewModelZone.deleteZone(id = _id)
                Toast.makeText(context,"La zona se ha eliminado correctamente",Toast.LENGTH_SHORT).show()
                navController.popBackStack()

            }
        }
    }

    //Textos
    var (textName, onValueChangeName) = rememberSaveable{ mutableStateOf(selectedZone.name) }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val nameOfNameError: String = "El nombre no puede contener ')(' ni ser mayor de 10"
    var (textNºmesas, onValueChangeNºmesas) = rememberSaveable{ mutableStateOf(selectedZone.nºTables.toString()) }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edición de Zona",color = Color.White)
                },
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {
                    IconButton(
                        onClick = {
                            onValueChangeDeleteItem(true)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Icon",
                            tint = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .padding(start = 20.dp,end = 20.dp),
                    content ={
                        item {
                            createRowListWithErrorMesaje(
                                text = "Nombre",
                                value = textName,
                                onValueChange = onValueChangeName,
                                validateError = mainViewModelZone::isValidNameOfZone,
                                errorMesaje = nameOfNameError,
                                changeError = nameErrorChange,
                                error = nameError,
                                mandatory = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
                            createRowList(
                                text = "NºMesas",
                                value = textNºmesas,
                                onValueChange = onValueChangeNºmesas,
                                enable = false,
                                KeyboardType = KeyboardType.Number
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.padding(10.dp))

                        }
                    }
                )
                Column(
                    Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Button(
                            onClick = {
                                textName = ""
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
                            Text(text = "Revertir cambios", fontSize = 15.sp)
                        }


                        Button(
                            onClick = {
                                if (mainViewModelZone.checkAllValidations(textNameOfZone = textName)) {
                                    val editZone = Zonas(_id = _id,name = textName,nºTables = textNºmesas.toInt())
                                    mainViewModelZone.editZone(zone = editZone)
                                    showToast.value = true
                                    textOfToast.value = "La zona se ha modificado correctamente"

                                } else {
                                    showToast.value = true
                                    textOfToast.value = "Debes de rellenar todos los campos correctamente"
                                }
                            },
                            contentPadding = PaddingValues(
                                start = 10.dp,
                                top = 6.dp,
                                end = 10.dp,
                                bottom = 6.dp
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(text = "Guardar cambios", fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    )
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview9() {

}