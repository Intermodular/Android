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
import sainero.dani.intermodular.ViewModels.ViewModelTipos
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainViewModelExtras
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.createEstructureOfAnotation
import java.util.regex.Pattern
import sainero.dani.intermodular.ViewsItems.createRowListWithErrorMesaje
import sainero.dani.intermodular.ViewsItems.createRowList
import sainero.dani.intermodular.ViewsItems.dropDownMenuWithNavigation


@Composable
fun MainProductNewType(
    mainViewModelExtras: MainViewModelExtras,
    mainViewModelTypes: MainViewModelTypes
) {

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


    Scaffold(
        scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Creación de Tipo",color = Color.White)
                },
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
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.88f),
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
                                onClick =  {clickButtonExtras(mainViewModelExtras,textName,textImg)}
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
                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, end = 10.dp)
                ) {
                    Button(
                        onClick = {
                            textName = ""
                            textImg = ""
                            allNamesOfExtras.clear()
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

                            newType = Tipos(0,textName,textImg,mainViewModelExtras._extras )
                            if (mainViewModelTypes.checkAllValidations(textNameOfType = textName)) {
                                mainViewModelTypes.uploadType(tipo = newType)
                                showToast.value = true
                                textOfToast.value = "El tipo se ha creado correctamente"
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


private fun clickButtonExtras(mainViewModelExtras: MainViewModelExtras, textName:String,textImg: String){
    mainViewModelExtras.tmpType = Tipos(0,textName,textImg, arrayListOf())
    navController.navigate("${Destinations.NewExtras.route}")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview18() {

}