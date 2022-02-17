package sainero.dani.intermodular.Views.Administration.Products.Types

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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

    //Variables de ayuda
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val showToast = remember { mutableStateOf(false) }
    val textOfToast = remember { mutableStateOf("") }
    var newType: Tipos
    var allNamesOfExtras:MutableList<String> = mutableListOf()
    val aplicateState = remember { mutableStateOf(true) }


    //Text
    var (textName, onValueChangeName) = rememberSaveable { mutableStateOf("") }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val textOfNameError: String = "El nombre no puede ser mayor a 14 carácteres ni esatar vacio"

    var (textImg, onValueChangeImg) = rememberSaveable { mutableStateOf("") }
    var textCompatibleExtras: MutableList<Extras> = remember { mutableListOf(Extras("",0f)) }




    if (showToast.value) {
        Toast.makeText(LocalContext.current,textOfToast.value, Toast.LENGTH_SHORT).show()
        showToast.value = false
    }

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

                mainViewModelExtras._extras.forEach{allNamesOfExtras.add(it.name)}
                dropDownMenuWithNavigation(
                    text = "Extras",
                    suggestions = allNamesOfExtras,
                    navigate = "${Destinations.NewExtras.route}",
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
                            if (checkAllValidations(textNameOfType = textName)) {
                                viewModelTipos.uploadType(tipo = newType)
                                showToast.value = true
                                textOfToast.value = "El tipo se ha creado correctamente"
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
fun DefaultPreview18() {

}