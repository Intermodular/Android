package sainero.dani.intermodular.Views.Administration.Zone.Table

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Zonas
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelZonas
import sainero.dani.intermodular.ViewsItems.createRowList
import sainero.dani.intermodular.ViewsItems.createRowListWithErrorMesaje
import sainero.dani.intermodular.ViewsItems.dropDownMenu
import sainero.dani.intermodular.ViewsItems.selectedDropDownMenu
import java.lang.NumberFormatException


@Composable
fun MainNewTable(
    mainViewModelTable: MainViewModelTable
){

    //Variables de ayuda
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val showToast = remember { mutableStateOf(false) }
    val textOfToast = remember { mutableStateOf("") }


    //Funciones extras
    if (showToast.value) {
        Toast.makeText(LocalContext.current,textOfToast.value, Toast.LENGTH_SHORT).show()
        showToast.value = false
    }

    //Textos
    var (textZone, onValueChangeZone) = rememberSaveable{ mutableStateOf("") }
    var (textState, onValueChangeState) = rememberSaveable{ mutableStateOf("")}
    var listOfTextState = rememberSaveable{ mutableListOf("Libre","Ocupada") }


    var (textNºChairs, onValueChangeNºChairs) = rememberSaveable{ mutableStateOf("0") }
    var (numChairsError,numChairsErrorChange) = remember { mutableStateOf(false) }
    val numChairsOfNumberError: String = "Debe ser un número entero"

    var (textNumber, onValueChangeNumber) = rememberSaveable{ mutableStateOf("") }
    var (numberError,numberErrorChange) = remember { mutableStateOf(false) }
    val nameOfNumberError: String = "Debe ser un número entero"

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nueva Mesa",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            GlobalVariables.navController.popBackStack()
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
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            ) {

                createRowListWithErrorMesaje(
                    text = "NºMesa",
                    value = textNumber,
                    onValueChange = onValueChangeNumber,
                    validateError = mainViewModelTable::isInteger,
                    errorMesaje = nameOfNumberError,
                    changeError = numberErrorChange,
                    error = numberError,
                    mandatory = true,
                    KeyboardType = KeyboardType.Number

                )

                var allNamesOfZone:MutableList<String> = mutableListOf()
                mainViewModelTable.zonesListResponse.forEach { allNamesOfZone.add(it.name) }
                onValueChangeZone(
                    selectedDropDownMenu(
                        text = "Zona",
                        suggestions = allNamesOfZone
                    )
                )

                createRowListWithErrorMesaje(
                    text = "NºSillas",
                    value = textNºChairs,
                    onValueChange = onValueChangeNºChairs,
                    validateError = mainViewModelTable::isInteger,
                    errorMesaje = numChairsOfNumberError,
                    changeError = numChairsErrorChange,
                    error = numChairsError,
                    mandatory = false,
                    KeyboardType = KeyboardType.Number

                )

                onValueChangeState(
                    selectedDropDownMenu(
                        text = "Estado",
                        suggestions = listOfTextState
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            onValueChangeZone("")
                            onValueChangeNºChairs("")
                            onValueChangeState("")
                            onValueChangeNumber("")
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

                            if(
                                mainViewModelTable.checkAllValidations(
                                    textNºChairs = textNºChairs,
                                    textNºMesas = textNumber
                                )
                            ) {
                                var newTable: Mesas = Mesas(
                                    _id = 0,
                                    zone = textZone,
                                    numChair = textNºChairs.toInt(),
                                    number = textNumber.toInt(),
                                    state = textState
                                )
                                mainViewModelTable.uploadMesa(mesa = newTable)
                                showToast.value = true
                                textOfToast.value = "La mesa se ha creado correctamente"
                                navController.popBackStack()
                            } else {
                                showToast.value = true
                                textOfToast.value = "Debes de rellenar todos los campos correctamente"
                            }

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview18() {

}