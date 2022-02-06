package sainero.dani.intermodular.Views.Cobrador

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Zonas
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelZonas

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
fun MainAccessToTables(viewModelMesas: ViewModelMesas, viewModelZonas: ViewModelZonas) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var scaffoldStateFilter = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))

    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    //Consulta BD
    var allTables: List<Mesas> = viewModelMesas.mesasListResponse
    var allZones: List<Zonas> = viewModelZonas.zonesListResponse

    //Filters
    var allStates: MutableList<String> = mutableListOf("Todas","Libre","Ocupado","Reservado")
    var textState = rememberSaveable{mutableStateOf("Todas")}

    var allNamesOfZones: MutableList<String> = mutableListOf()
    allNamesOfZones.add("Todas")
    allZones.forEach { allNamesOfZones.add(it.name) }
    var textSelectedZone = rememberSaveable{ mutableStateOf("Todas")}

    //Filters
    var nºMesas by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    },
                )
            },
            drawerShape = MaterialTheme.shapes.large,
            drawerScrimColor= DrawerDefaults.scrimColor,
            drawerContent = {
                Scaffold(
                    scaffoldState = scaffoldStateFilter,
                    content = {
                        Column(
                            verticalArrangement = Arrangement.Center
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
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "NºMesa")
                                    OutlinedTextField(
                                        value = nºMesas,
                                        onValueChange = {
                                            nºMesas = it
                                        },
                                        placeholder = { Text("NºMesas") },
                                        singleLine = true,
                                        modifier = Modifier
                                            .padding(start = 50.dp, end = 50.dp)
                                            .onKeyEvent {
                                                if (it.nativeKeyEvent.keyCode.equals(KeyEvent.KEYCODE_ENTER)) {
                                                    true
                                                }
                                                false
                                            }
                                    )
                                }
                            }
////////////////////

                            textSelectedZone.value = selectedDropDownMenu(text = "Zona",suggestions = allNamesOfZones)
                            textState.value = selectedDropDownMenu(text = "Estado",suggestions = allStates)

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "NºMesa")
                                    OutlinedTextField(
                                        value = nºMesas,
                                        onValueChange = {
                                            nºMesas = it
                                        },
                                        placeholder = { Text("NºMesas") },
                                        singleLine = true,
                                        modifier = Modifier
                                            .padding(start = 50.dp, end = 50.dp)
                                            .fillMaxWidth()
                                            .onKeyEvent {
                                                if (it.nativeKeyEvent.keyCode.equals(KeyEvent.KEYCODE_ENTER)) {
                                                    true
                                                }
                                                false
                                            }
                                    )
                                }
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                //Aplicar filtros
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

                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(120.dp),
                        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
                    ) {
                        for (i in allTables) {
                            item {
                                Box(Modifier.padding(10.dp)) {
                                    Button(
                                        onClick = {
                                            navController.navigate(Destinations.CreateOrder.route + "/${i._id}")
                                        },
                                        contentPadding = PaddingValues(10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = checkState(i.state),
                                            contentColor = Color.Blue
                                        )
                                    ) {
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
            },
        )
    }
}


@Composable
private fun selectedDropDownMenu(text: String,suggestions: List<String>): String {
    Spacer(modifier = Modifier.padding(4.dp))
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(suggestions[0]) }
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

fun createTables() {

}


@Preview(showBackground = true)
@ExperimentalFoundationApi
@Composable
fun DefaultPreview3() {
    //MainAccessToTables()
}