package sainero.dani.intermodular.Views.Administration.Products.Types

import android.os.Bundle
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

class ProductEditType : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainProductEditType(id: Int,viewModelTipos: ViewModelTipos, mainViewModelExtras: MainViewModelExtras, stateView: String) {
    //Posible consulta en la Base de datos ¿? (but is ok)
    var selectedType: Tipos = Tipos(_id = id,"","", arrayListOf())
    viewModelTipos.typeListResponse.forEach{ if (it._id.equals(id))  selectedType = it }

    var (valueOfEditExtras, onChangeEditExtras) = remember { mutableStateOf(false) }
    val aplicateState = remember { mutableStateOf(true) }
    var extrasCompatibles: MutableList<Extras> = mutableListOf()

if (aplicateState.value) {
    when (stateView){
        "new" -> {
            mainViewModelExtras._extras.clear()
            mainViewModelExtras._tmpExtras.clear()
            selectedType.compatibleExtras.forEach{ mainViewModelExtras.addExtras(it) }
            mainViewModelExtras._tmpExtras = mainViewModelExtras._extras.toMutableList()
            aplicateState.value = false

        }
        "edit" -> {

            mainViewModelExtras._extras = mainViewModelExtras._tmpExtras.toMutableList()
            aplicateState.value = false
        }
        "cancel" -> {
            mainViewModelExtras._tmpExtras = mainViewModelExtras._extras.toMutableList()
            aplicateState.value = false

        }
        else  ->{
            aplicateState.value = false
        }
    }
}






    editType(id = id, viewModelTipos = viewModelTipos,onChangeEditExtras = onChangeEditExtras,selectedType = selectedType,mainviewModelExtras = mainViewModelExtras)

}


@Composable
private fun editType(id: Int, viewModelTipos: ViewModelTipos, onChangeEditExtras: (Boolean) -> Unit, selectedType: Tipos,mainviewModelExtras: MainViewModelExtras) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }
    var deleteType = remember { mutableStateOf(false) }
    var state = remember { mutableStateOf(0) }




    //Text

    var allNamesOfExtras: MutableList<String> = mutableListOf()
    selectedType.compatibleExtras.forEach{allNamesOfExtras.add(it.name)}

    var (textName, onValueChangeName) = rememberSaveable { mutableStateOf(selectedType.name) }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val textOfNameError: String = "El nombre no puede ser mayor a 14 carácteres"

    var (textImg, onValueChangeImg) = rememberSaveable { mutableStateOf(selectedType.img) }
    var (textCompatibleExtras, onValueCompatibleExtras) = rememberSaveable { mutableStateOf(selectedType.compatibleExtras) }


    //Funciones extras a realizar
    if (deleteType.value) {
        confirmDeleteType(viewModelTipos = viewModelTipos, id = id)
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
                    numericTextBoard = false
                )

                createRowList(text = "Img", value = textImg, onValueChange = onValueChangeImg)

                dropDownMenu(text = "Extras", suggestions = allNamesOfExtras, idOfItem = id,onChangeEditExtras = onChangeEditExtras, mainviewModelExtras = mainviewModelExtras,selectedType = selectedType)

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

                            //Modificar el eleemnto padre¿?
                            //selectedType = Tipos(id,textName,textImg,textCompatibleExtras)
                            viewModelTipos.editType(selectedType)

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
private fun createRowListWithErrorMesaje(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    validateError: (String) -> Boolean,
    errorMesaje: String,
    changeError: (Boolean) -> Unit,
    error: Boolean,
    mandatory: Boolean,
    numericTextBoard : Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    changeError(!validateError(it))
                },
                placeholder = { Text(text) },
                label = { Text(text = text) },
                isError = error,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = if (numericTextBoard) KeyboardType.Number else KeyboardType.Text),

                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
            )
            val assistiveElementText = if (error) errorMesaje else if (mandatory) "*Obligatorio" else ""
            val assistiveElementColor = if (error) {
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            }
            Text(
                text = assistiveElementText,
                color = assistiveElementColor,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 10.dp, end = 20.dp)
            )
        }
    }
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


@Composable
private fun dropDownMenu(text: String,suggestions: List<String>, idOfItem: Int, onChangeEditExtras: (Boolean) -> Unit, mainviewModelExtras: MainViewModelExtras,selectedType: Tipos) {
    Spacer(modifier = Modifier.padding(4.dp))
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }
    var textfieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    var editItem = remember{ mutableStateOf(false)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        Column() {

            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                enabled = false,
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(icon, "arrowExpanded",
                        Modifier.clickable { expanded = !expanded })
                },
                leadingIcon = {
                    Icon(Icons.Default.Edit,"Edit ${text}",
                        Modifier.clickable{
                            editItem.value = true

                            navController.navigate("${Destinations.Extras.route}/${idOfItem}")
                        })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                mainviewModelExtras._extras.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedText = label.name
                        expanded = false
                    }) {
                        Text(text = label.name)
                    }
                }
            }
        }
    }
}

//Validaciones
private fun isValidNameOfType(text: String) = Pattern.compile("^[^)(]{1,14}$", Pattern.CASE_INSENSITIVE).matcher(text).find()

@Preview(showBackground = true)
@Composable
fun DefaultPreview15() {

}