package sainero.dani.intermodular.Views.Administration.Products.Types

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Tipos
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelExtras
import sainero.dani.intermodular.ViewModels.ViewModelTipos
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainViewModelExtras
import java.util.regex.Pattern
import sainero.dani.intermodular.ViewsItems.createRowListWithErrorMesaje
import sainero.dani.intermodular.ViewsItems.createRowList
import sainero.dani.intermodular.ViewsItems.dropDownMenuWithNavigation


class ProductEditType : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainProductEditType(
    id: Int,
    viewModelTipos: ViewModelTipos,
    mainViewModelExtras: MainViewModelExtras
) {
    //Posible consulta en la Base de datos ¿? (but is ok)
    var selectedType: Tipos = Tipos(_id = id,"","", arrayListOf())
    viewModelTipos.typeListResponse.forEach{ if (it._id.equals(id))  selectedType = it }

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

    editType(id = id, viewModelTipos = viewModelTipos,onChangeEditExtras = onChangeEditExtras,selectedType = selectedType,mainViewModelExtras = mainViewModelExtras)

}


@Composable
private fun editType(id: Int, viewModelTipos: ViewModelTipos, onChangeEditExtras: (Boolean) -> Unit, selectedType: Tipos,mainViewModelExtras: MainViewModelExtras) {

    //Variables de ayuda
    val showToast = remember { mutableStateOf(false) }
    val textOfToast = remember { mutableStateOf("") }
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }
    var deleteType = remember { mutableStateOf(false) }




    //Text

    var allNamesOfExtras: MutableList<String> = mutableListOf()
    //selectedType.compatibleExtras.forEach{allNamesOfExtras.add(it.name)}

    var (textName, onValueChangeName) = rememberSaveable { mutableStateOf(selectedType.name) }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val textOfNameError: String = "El nombre no puede ser mayor a 14 carácteres"

    var (textImg, onValueChangeImg) = rememberSaveable { mutableStateOf(selectedType.img) }
    var (textCompatibleExtras, onValueCompatibleExtras) = rememberSaveable { mutableStateOf(selectedType.compatibleExtras) }


    //Funciones extras a realizar
    if (deleteType.value) {
        confirmDeleteType(viewModelTipos = viewModelTipos, id = id)
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
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {
                    IconButton(
                        onClick = {
                            deleteType.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar tipo",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }

                    Box (Modifier.wrapContentSize()){
                        IconButton(onClick = {
                            expanded.value = true
                            result.value = "More icon clicked"
                        }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "More icon"
                            )
                        }

                        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                }) {
                                Text(text = "")
                            }
                        }
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
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            ) {

                createRowListWithErrorMesaje(
                    text = "Name",
                    value = textName,
                    onValueChange = onValueChangeName,
                    validateError = ::isValidNameOfType,
                    errorMesaje = textOfNameError,
                    changeError = nameErrorChange,
                    error = nameError,
                    mandatory = true,
                    KeyboardType = KeyboardType.Text
                )

                mainViewModelExtras._extras.forEach{allNamesOfExtras.add(it.name)}
                dropDownMenuWithNavigation(
                    text = "Extras",
                    suggestions = allNamesOfExtras,
                    navigate = "${Destinations.Extras.route}/${id}",
                )

                createRowList(
                    text = "Img",
                    value = textImg,
                    onValueChange = onValueChangeImg,
                    enable = true,
                    KeyboardType = KeyboardType.Text
                )


                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            textName = ""
                            textImg = ""
                            textCompatibleExtras = arrayListOf()

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

                            var updateType: Tipos = Tipos(id,textName,textImg,mainViewModelExtras._extras)
                            if (checkAllValidations(textNameOfType = textName)) {
                                viewModelTipos.editType(tipo = updateType)
                                showToast.value = true
                                textOfToast.value = "El tipo se ha actualizado correctamente"
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
        })
}

@Composable
private fun confirmDeleteType(viewModelTipos: ViewModelTipos, id: Int) {
    MaterialTheme {

        Column {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "¿Seguro que desea eliminar el tipo seleccionado?")
                },
                text = {
                    Text("No podrás volver a recuperarlo")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModelTipos.deleteType(id)
                            navController.navigate(Destinations.ProductTypeManager.route)
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
                            navController.navigate("${Destinations.ProductEditType.route}/${id}")
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


//Validaciones
private fun isValidNameOfType(text: String) = Pattern.compile("^[^)(]{1,14}$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun checkAllValidations (
    textNameOfType: String
): Boolean {
    if (!isValidNameOfType(text = textNameOfType)) return false
    return true
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview15() {

}