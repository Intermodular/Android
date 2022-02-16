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
import sainero.dani.intermodular.ViewsItems.createRowListWithErrorMesaje
import sainero.dani.intermodular.ViewsItems.createRowList
import sainero.dani.intermodular.ViewsItems.dropDownMenuWithNavigation

class ProductNewTyp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainProductNewType(viewModelTipos: ViewModelTipos, mainViewModelExtras: MainViewModelExtras) {
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
        when (mainViewModelExtras.extrasState){
            "New" -> {
                mainViewModelExtras._extras.clear()
                mainViewModelExtras._tmpExtras.clear()
                mainViewModelExtras.tmpType = Tipos(0,"","", arrayListOf())
                //selectedType.compatibleExtras.forEach{ mainViewModelExtras.addExtras(it) }
                mainViewModelExtras._tmpExtras = mainViewModelExtras._extras.toMutableList()
                aplicateState.value = false

            }
            "Edit" -> {
                onValueChangeName(mainViewModelExtras.tmpType.name)
                onValueChangeImg(mainViewModelExtras.tmpType.img)

                mainViewModelExtras._extras = mainViewModelExtras._tmpExtras.toMutableList()
                aplicateState.value = false
            }
            "Cancel" -> {

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

                dropDownMenu(
                    text = "Extras",
                    suggestions = allNamesOfExtras,
                    textName = textName,
                    textImg = textImg,
                    textCompatibleExtras = textCompatibleExtras,
                    mainViewModelExtras = mainViewModelExtras,
                    navigation = "${Destinations.NewExtras.route}/${0}"
                )

                /*
                mainViewModelExtras._extras.forEach{allNamesOfExtras.add(it.name)}
                dropDownMenuWithNavigation(
                    text = "Extras",
                    suggestions = allNamesOfExtras,
                    navigate = "${Destinations.Extras.route}/${0}",
                )*/

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
                            newType = Tipos(0,textName,textImg,mainViewModelExtras._extras )
                            viewModelTipos.uploadType(newType)
                            navController.popBackStack()
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
private fun dropDownMenu(
    text: String,
    suggestions: List<String>,
    textName: String,
    textImg: String,
    textCompatibleExtras: MutableList<Extras>,
    mainViewModelExtras: MainViewModelExtras,
    navigation: String
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
                            navController.navigate(navigation)
                        })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedText = label
                        expanded = false
                    }) {
                        Text(text = label)
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