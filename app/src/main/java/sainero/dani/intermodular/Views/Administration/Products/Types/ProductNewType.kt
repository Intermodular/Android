package sainero.dani.intermodular.Views.Administration.Products.Types

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.DataClass.Tipos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelTipos
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainViewModelExtras
import java.util.regex.Pattern

class ProductNewTyp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainProductNewType(viewModelTipos: ViewModelTipos, mainViewModelExtras: MainViewModelExtras, stateView: String) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }


    var newType: Tipos = Tipos(_id = 0,"","", arrayListOf())
    var allNamesOfExtras:MutableList<String> = mutableListOf()
    var allExtras: MutableList<Extras> = mutableListOf()

    //Text
    var (textName, onValueChangeName) = rememberSaveable { mutableStateOf("") }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val textOfNameError: String = "El nombre no puede ser mayor a 14 carácteres"

    var (textImg, onValueChangeImg) = rememberSaveable { mutableStateOf("") }
    var textCompatibleExtras: MutableList<Extras> = remember { mutableListOf(Extras("",0f)) }

    val aplicateState = remember { mutableStateOf(true) }

    //Control de acciones de la ventana
    if (aplicateState.value) {
        when (stateView){
            "new" -> {
                mainViewModelExtras._extras.clear()
                mainViewModelExtras._tmpExtras.clear()
                mainViewModelExtras.tmpType = Tipos(0,"","", arrayListOf())
                //selectedType.compatibleExtras.forEach{ mainViewModelExtras.addExtras(it) }
                mainViewModelExtras._tmpExtras = mainViewModelExtras._extras.toMutableList()
                aplicateState.value = false

            }
            "edit" -> {
                onValueChangeName(mainViewModelExtras.tmpType.name)
                onValueChangeImg(mainViewModelExtras.tmpType.img)

                mainViewModelExtras._extras = mainViewModelExtras._tmpExtras.toMutableList()
                aplicateState.value = false
            }
            "cancel" -> {

                onValueChangeName(mainViewModelExtras.tmpType.name)
                onValueChangeImg(mainViewModelExtras.tmpType.img)

                mainViewModelExtras._tmpExtras = mainViewModelExtras._extras.toMutableList()
                aplicateState.value = false

            }
            else  ->{
                aplicateState.value = false
            }
        }
    }


    //Ventana
    Scaffold(

        scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Creación de Tipo",color = Color.White)
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

                dropDownMenu(
                    text = "Extras",
                    suggestions = allNamesOfExtras,
                    idOfItem = 0,
                    textName = textName,
                    textImg = textImg,
                    textCompatibleExtras = textCompatibleExtras,
                    mainViewModelExtras = mainViewModelExtras
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
                            allNamesOfExtras.clear()
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
                            newType = Tipos(0,textName,textImg,allExtras )

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
                            GlobalVariables.navController.navigate(Destinations.ProductTypeManager.route)
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
                            GlobalVariables.navController.navigate("${Destinations.ProductEditType.route}/${id}")
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
private fun dropDownMenu(
    text: String,
    suggestions: List<String>,
    idOfItem: Int,
    textName: String,
    textImg: String,
    textCompatibleExtras: MutableList<Extras>,
    mainViewModelExtras: MainViewModelExtras
) {
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
                            mainViewModelExtras.tmpType = Tipos(0,textName,textImg,textCompatibleExtras )
                            navController.navigate("${Destinations.NewExtras.route}/${idOfItem}")


                        })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                mainViewModelExtras._extras.forEach { label ->
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
fun DefaultPreview18() {

}