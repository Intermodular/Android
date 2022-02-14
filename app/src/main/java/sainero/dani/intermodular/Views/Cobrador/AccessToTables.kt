package sainero.dani.intermodular.Views.Cobrador

import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.view.KeyEvent
import android.widget.PopupMenu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import okhttp3.internal.assertThreadDoesntHoldLock
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Zonas
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelZonas
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.MainViewModelCreateOrder

@ExperimentalFoundationApi

class AccessToTables : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainAccessToTables(viewModelMesas: ViewModelMesas, viewModelZonas: ViewModelZonas, mainViewModelCreateOrder: MainViewModelCreateOrder) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var scaffoldStateFilter = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))

    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    //Consulta BD
    var allTables: List<Mesas> = viewModelMesas.mesasListResponse
    var allZones: List<Zonas> = viewModelZonas.zonesListResponse

    //Filters
    var allStates: MutableList<String> = mutableListOf("Todas","Libre","Ocupada","Reservada")
    var textState = rememberSaveable{mutableStateOf("Todas")}

    var allNamesOfZones: MutableList<String> = mutableListOf()
    allNamesOfZones.add("Todas")
    allZones.forEach { allNamesOfZones.add(it.name) }
    var textSelectedZone = rememberSaveable{ mutableStateOf("Todas")}

    var (nºMesa, onValueChangeNºMesa) = rememberSaveable{ mutableStateOf("") }
    var (nºComensales, onValueChangeNºComensales) = rememberSaveable{ mutableStateOf("") }

    val restartFilters = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Menú de mesas", color = Color.White)
                    },
                    backgroundColor = Color.Blue,
                    elevation = AppBarDefaults.TopAppBarElevation,
                    actions = {
                        Box(Modifier.wrapContentSize()) {
                            IconButton(onClick = {
                                expanded.value = true
                                result.value = "More icon clicked"
                            }) {
                                Icon(
                                    Icons.Filled.MoreVert,
                                    contentDescription = "Localized description"
                                )
                            }

                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false }) {
                                DropdownMenuItem(
                                    onClick = {
                                        expanded.value = false
                                        navController.navigate(Destinations.AccessToTables.route)
                                    }) {
                                    Text(text = "Gestionar Reservas")
                                }
                                Divider()
                                DropdownMenuItem(
                                    onClick = {
                                        expanded.value = false
                                        navController.navigate(Destinations.MainAdministrationActivity.route)
                                    }) {
                                    Text(text = "Entrar como Administrador")
                                }
                                Divider()
                                DropdownMenuItem(
                                    onClick = {
                                        expanded.value = false
                                        navController.navigate(Destinations.Login.route) {
                                            popUpTo(0)
                                        }

                                    }) {
                                    Text(text = "Cerrar sesión")
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        // show drawer icon
                        IconButton(
                            onClick = {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "")
                        }
                    }

                )
            },
            drawerShape = MaterialTheme.shapes.large,
            drawerScrimColor= DrawerDefaults.scrimColor,
            drawerContent = {
                Scaffold(
                    scaffoldState = scaffoldStateFilter,
                    content = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp)
                                    .background(MaterialTheme.colors.primary),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Filtros",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                            }
                            Spacer(modifier = Modifier.padding(10.dp))


                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Spacer(modifier = Modifier.padding(10.dp))
                                Text(text = "NºMesa:", Modifier.width(100.dp))
                                OutlinedTextField(
                                    value = nºMesa,
                                    onValueChange = {
                                        onValueChangeNºComensales("")
                                        onValueChangeNºMesa(it)
                                        restartFilters.value = true
                                    },
                                    placeholder = { Text("NºMesa") },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                    label = { Text(text = "NºMesa") },
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 20.dp)
                                )
                            }

                            //Reiniciar valores ¿?
                            textSelectedZone.value = selectedDropDownMenu(text = "Zona",suggestions = allNamesOfZones,onValueChangeNºMesa = onValueChangeNºMesa)
                            textState.value = selectedDropDownMenu(text = "Estado",suggestions = allStates,onValueChangeNºMesa = onValueChangeNºMesa)

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Spacer(modifier = Modifier.padding(10.dp))
                                Text(text = "NºComensales:", Modifier.width(100.dp))
                                OutlinedTextField(
                                    value = nºComensales,
                                    onValueChange = {
                                        onValueChangeNºComensales(it)
                                        nºMesa = ""
                                    },
                                    placeholder = { Text("NºComensales") },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                    label = { Text(text = "NºComensales") },
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 20.dp)
                                )
                            }


                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search Icon",
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                )



            },
            content = {
                val scrollState = rememberScrollState()
                Column(
                    verticalArrangement = Arrangement.Center
                ) {

                        filterByAllFilters(
                            allTables = allTables,
                            nºMesasFilter = nºMesa,
                            zoneFilter = textSelectedZone.value,
                            stateFilter = textState.value,
                            dinersFilter = nºComensales,
                            mainViewModelCreateOrder = mainViewModelCreateOrder
                        )


                }

            },
        )
    }
}


@Composable
private fun selectedDropDownMenu(text: String,suggestions: List<String>,onValueChangeNºMesa: (String) -> Unit): String {
    Spacer(modifier = Modifier.padding(4.dp))
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Todas") }
    var textfieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "${text}:", Modifier.width(100.dp))
        Column() {

            OutlinedTextField(
                value = selectedText,
                onValueChange = {  },
                enabled = false,
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
                    .onGloballyPositioned {
                        textfieldSize = it.size.toSize()
                    },
                trailingIcon = {
                    Icon(icon, "arrowExpanded",
                        Modifier.clickable { expanded = !expanded })
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
                        onValueChangeNºMesa("")
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
    return selectedText
}

private fun checkState(state: String): Color {
    when(state){
        "Libre" -> return Color.Green
        "Ocupada" -> return Color.Red
        "Reservada" -> return Color.Yellow
        else -> {
            Color.White
        }
    }
    return Color.White
}

@Composable
private fun createRowList(text: String, value: String, onValueChange: (String) -> Unit, KeyboardType: KeyboardType) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "${text}:", Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(text) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType),
            label = { Text(text = text) },
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
        )
    }
}

@ExperimentalFoundationApi
@Composable
private fun filterByAllFilters(allTables: List<Mesas>, nºMesasFilter: String,zoneFilter: String, stateFilter: String, dinersFilter: String,mainViewModelCreateOrder : MainViewModelCreateOrder) {

    //Obtener todas las mesas (Luego le vamos restando las que no cumplan la condición)
    val allFilterTables: MutableList<Mesas> = mutableListOf()
    val removeTables: MutableList<Mesas> = mutableListOf()
    allTables.forEach { allFilterTables.add(it) }
    var listOfAllFilterTables: List<Mesas> = mutableListOf()
    allFilterTables.sortedBy {it.number}
    if (!nºMesasFilter.equals("")) {
        allFilterTables.forEach{
            if (it.number.equals(nºMesasFilter.toInt())) listOfAllFilterTables = listOf(it)
        }
    } else {
        //State
        if (!stateFilter.equals("Todas"))
            allFilterTables.forEach {
                if (!it.state.equals(stateFilter)) removeTables.add(it)
            }

        //Zone
        if (!zoneFilter.equals("Todas"))
            allFilterTables.forEach {
                if (!it.zone.equals(zoneFilter) ) removeTables.add(it)
            }

        //Diners
        if (!dinersFilter.equals(""))
            allFilterTables.forEach{
                if (it.numChair < dinersFilter.toInt()) removeTables.add(it)
            }


        removeTables.forEach{
            allFilterTables.remove(it)
        }

        if(!dinersFilter.equals("")) listOfAllFilterTables = allFilterTables.sortedBy {it.numChair}
        else listOfAllFilterTables = allFilterTables
    }
    createTables(allFilterTables = listOfAllFilterTables,mainViewModelCreateOrder = mainViewModelCreateOrder)
}

@ExperimentalFoundationApi
@Composable
private fun createTables(allFilterTables: List<Mesas>, mainViewModelCreateOrder: MainViewModelCreateOrder) {
    var showMenu by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {
        for (i in allFilterTables) {
            item {
                /*val infiniteTransition = rememberInfiniteTransition()
                val scale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000),
                        repeatMode = RepeatMode.Reverse
                    )
                )*/
                Box(Modifier.padding(10.dp)) {
                    Button(
                        onClick = {
                            mainViewModelCreateOrder.clearAllVariables = true
                           // mainViewModelCreateOrder.getOrder = true

                            if (i.state.equals("Ocupada")) {
                                mainViewModelCreateOrder.editOrder = true
                                navController.navigate(Destinations.CreateOrderWithOrder.route + "/${i._id}")
                            }
                            else {
                                mainViewModelCreateOrder.editOrder = false
                                navController.navigate(Destinations.CreateOrder.route + "/${i._id}")
                            }
                        },
                        modifier = Modifier,

                           // .scale(scale)
                            /*
                            .pointerInput(Unit){
                              detectTapGestures (
                                  onLongPress = {
                                        //Evento al mantener
                                    showMenu = true


                                  }
                              )

                        }*/
                        contentPadding = PaddingValues(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = checkState(i.state),
                            contentColor = Color.Blue
                        )
                    ) {
                        if(showMenu) {
                            Popup() {
                                Column(
                                    verticalArrangement = Arrangement.SpaceAround
                                ) {
                                    Text(text = "Liberar mesa")
                                }
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "${i.zone} (${i.numChair})",
                                fontSize = 10.sp,
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = i.number.toString(),
                                fontSize = 20.sp,
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.padding(4.dp))
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@ExperimentalFoundationApi
@Composable
fun DefaultPreview3() {
    //MainAccessToTables()
}