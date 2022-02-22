package sainero.dani.intermodular.Views.Administration.Products.Types

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.DataClass.Tipos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainViewModelExtras
import sainero.dani.intermodular.ViewsItems.createRowListWithErrorMesaje
import sainero.dani.intermodular.ViewsItems.createRowList
import sainero.dani.intermodular.ViewsItems.dropDownMenuWithNavigation
import sainero.dani.intermodular.ViewsItems.confirmAlertDialog


@Composable
fun MainProductEditType(
    id: Int,
    mainViewModelExtras: MainViewModelExtras,
    mainViewModelTypes: MainViewModelTypes
) {

    //Posible consulta en la Base de datos ¿? (but is ok)
    var selectedType: Tipos = Tipos(_id = id,"","", arrayListOf())
    mainViewModelTypes.typeListResponse.forEach{ if (it._id.equals(id))  selectedType = it }

    var (valueOfEditExtras, onChangeEditExtras) = remember { mutableStateOf(false) }
    val aplicateState = remember { mutableStateOf(true) }
    var extrasCompatibles: MutableList<Extras> = mutableListOf()



if (aplicateState.value) {
    when (mainViewModelExtras.extrasState){
        "New" -> {
            mainViewModelExtras._extras.clear()
            mainViewModelExtras._tmpExtras.clear()
            selectedType.compatibleExtras.forEach{ mainViewModelExtras.addExtras(it) }
            mainViewModelExtras._tmpExtras = mainViewModelExtras._extras.toMutableList()
            aplicateState.value = false

        }
        "Edit" -> {

            mainViewModelExtras._extras = mainViewModelExtras._tmpExtras.toMutableList()
            aplicateState.value = false
        }
        "Cancel" -> {
            mainViewModelExtras._tmpExtras = mainViewModelExtras._extras.toMutableList()
            aplicateState.value = false

        }
        else  ->{
            aplicateState.value = false
        }
    }

}

    editType(id = id, mainViewModelTypes = mainViewModelTypes,onChangeEditExtras = onChangeEditExtras,selectedType = selectedType,mainViewModelExtras = mainViewModelExtras)

}


@Composable
private fun editType(id: Int, mainViewModelTypes: MainViewModelTypes, onChangeEditExtras: (Boolean) -> Unit, selectedType: Tipos,mainViewModelExtras: MainViewModelExtras) {

    //Variables de ayuda
    val showToast = remember { mutableStateOf(false) }
    val textOfToast = remember { mutableStateOf("") }
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }
    var (deleteItem,onValueChangeDeleteItem) = remember { mutableStateOf(false) }
    var context = LocalContext.current



    //Text

    var allNamesOfExtras: MutableList<String> = mutableListOf()
    var (textName, onValueChangeName) = rememberSaveable { mutableStateOf(selectedType.name) }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val textOfNameError: String = "El nombre no puede ser mayor a 14 carácteres"

    var (textImg, onValueChangeImg) = rememberSaveable { mutableStateOf(selectedType.img) }
    var (textCompatibleExtras, onValueCompatibleExtras) = rememberSaveable { mutableStateOf(selectedType.compatibleExtras) }


    //Funciones extras a realizar
    if (deleteItem) {
        var title: String = "¿Seguro que desea eliminar el tipo seleccionado?"
        var subtitle: String = "No podrás volver a recuperarlo"

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteItem,
        ) {
            if (it) {
                mainViewModelTypes.deleteType(id = id)
                Toast.makeText(context,"El tipo se ha eliminado correctamente",Toast.LENGTH_SHORT).show()
                navController.popBackStack()

            }
        }
    }

    if (showToast.value) {
        Toast.makeText(LocalContext.current,textOfToast.value, Toast.LENGTH_SHORT).show()
        showToast.value = false
    }

    //Ventana
    Scaffold(

        scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edición de Tipo",color = Color.White)
                },
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {
                    IconButton(
                        onClick = {
                            onValueChangeDeleteItem(true)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar tipo",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
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
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.88f),
                    content = {
                        item {
                            Spacer(modifier = Modifier.padding(10.dp))
                            createRowListWithErrorMesaje(
                                text = "Name",
                                value = textName,
                                onValueChange = onValueChangeName,
                                validateError = mainViewModelTypes::isValidNameOfType,
                                errorMesaje = textOfNameError,
                                changeError = nameErrorChange,
                                error = nameError,
                                mandatory = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
                            mainViewModelExtras._extras.forEach{allNamesOfExtras.add(it.name)}
                            dropDownMenuWithNavigation(
                                text = "Extras",
                                suggestions = allNamesOfExtras,
                                onClick = ::clickButtonExtras,
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.padding(top = 8.dp))
                            createRowList(
                                text = "Img",
                                value = textImg,
                                onValueChange = onValueChangeImg,
                                enable = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp)
                ) {
                    Button(
                        onClick = {
                            onValueChangeName(selectedType.name)
                            onValueChangeImg(selectedType.img)
                            textCompatibleExtras = arrayListOf()

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
                            var updateType: Tipos = Tipos(id,textName,textImg,mainViewModelExtras._extras)
                            if (mainViewModelTypes.checkAllValidations(textNameOfType = textName)) {
                                mainViewModelTypes.editType(tipo = updateType)
                                showToast.value = true
                                textOfToast.value = "El tipo se ha actualizado correctamente"
                                navController.popBackStack()
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
                    ) {
                        Text(text = "Guardar cambios", fontSize = 15.sp)
                    }
                }
            }
        }
    )
}

private fun clickButtonExtras(){
    navController.navigate("${Destinations.Extras.route}")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview15() {

}