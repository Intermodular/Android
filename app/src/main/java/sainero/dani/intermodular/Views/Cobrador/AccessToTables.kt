package sainero.dani.intermodular.Views.Cobrador

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.*
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.currentValidateUser
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelZonas
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.MainViewModelCreateOrder
import sainero.dani.intermodular.Views.SplashScreen.Splash


@ExperimentalFoundationApi
@Composable
fun MainAccessToTables(
    viewModelZonas: ViewModelZonas,
    mainViewModelCreateOrder: MainViewModelCreateOrder
) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    var scaffoldStateFilter = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    val (disableAlert,onValueChangeDisableAlert) = remember { mutableStateOf(false) }

    val (selectedTable,onValueChangeSelectedTable) = remember { mutableStateOf(Mesas(_id = 0,zone = "",numChair = 0,number =0,state = ""))}

    if (disableAlert) {
        confirmCreateOrder(
            onValueChangeDisableAlert = onValueChangeDisableAlert,
            mainViewModelCreateOrder = mainViewModelCreateOrder,
            table = selectedTable,
        )
    }
    val (refreshTables, onValueChangeRefreshTables) = remember { mutableStateOf(true) }
    reloadTables(mainViewModelCreateOrder = mainViewModelCreateOrder)
    val expanded = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    //Consulta BD
    var allTables: List<Mesas> = mainViewModelCreateOrder.mesasListResponse
    var allZones: List<Zonas> = viewModelZonas.zonesListResponse

    //Filters
    var allStates: MutableList<String> = mutableListOf("Todas","Libre","Ocupada","Reservada")
    var textState = rememberSaveable{mutableStateOf("Todas")}

    var allNamesOfZones: MutableList<String> = mutableListOf()
    allNamesOfZones.add("Todas")
    allZones.forEach { allNamesOfZones.add(it.name) }
    var textSelectedZone = rememberSaveable{ mutableStateOf("Todas")}

    var (n??Mesa, onValueChangeN??Mesa) = rememberSaveable{ mutableStateOf("") }
    var (n??Comensales, onValueChangeN??Comensales) = rememberSaveable{ mutableStateOf("") }

    val restartFilters = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Scaffold(
            snackbarHost = { state -> MySnackHost(
                state = state,
                mainViewModelCreateOrder = mainViewModelCreateOrder,
                selectedTable = selectedTable,
                onValueChangeRefreshTables = onValueChangeRefreshTables,
                onValueChangeSelectedTable = onValueChangeSelectedTable
            ) },
            scaffoldState = scaffoldState,
            
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Men?? de mesas", color = Color.White)
                    },
                    elevation = AppBarDefaults.TopAppBarElevation,
                    actions = {
                        Box(Modifier.wrapContentSize()) {
                            IconButton(onClick = {
                                expanded.value = true
                            }) {
                                Icon(
                                    Icons.Filled.MoreVert,
                                    contentDescription = "Localized description",
                                    tint = Color.White
                                )
                            }

                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false }) {
                                if (currentValidateUser.rol.equals("Administrador")){
                                    DropdownMenuItem(
                                        onClick = {
                                            expanded.value = false
                                            navController.navigate(Destinations.MainAdministrationActivity.route)
                                        }) {
                                        Text(text = "Entrar como Administrador")
                                    }
                                    Divider()
                                }
                                DropdownMenuItem(
                                    onClick = {
                                        expanded.value = false
                                        navController.navigate(Destinations.Login.route) {
                                            popUpTo(0)
                                        }
                                    }) {
                                    Text(text = "Cerrar sesi??n")
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                        ) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
            drawerShape = MaterialTheme.shapes.medium,
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
                                Text(text = "N??Mesa:", Modifier.width(100.dp))
                                OutlinedTextField(
                                    value = n??Mesa,
                                    onValueChange = {
                                        onValueChangeN??Comensales("")
                                        onValueChangeN??Mesa(it)
                                        restartFilters.value = true
                                    },
                                    placeholder = { Text("N??Mesa") },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                    label = { Text(text = "N??Mesa") },
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 20.dp)
                                )
                            }

                            textSelectedZone.value = selectedDropDownMenu(text = "Zona",suggestions = allNamesOfZones,onValueChangeN??Mesa = onValueChangeN??Mesa)
                            textState.value = selectedDropDownMenu(text = "Estado",suggestions = allStates,onValueChangeN??Mesa = onValueChangeN??Mesa)

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Spacer(modifier = Modifier.padding(10.dp))
                                Text(text = "N??Comensales:", Modifier.width(100.dp))
                                OutlinedTextField(
                                    value = n??Comensales,
                                    onValueChange = {
                                        onValueChangeN??Comensales(it)
                                        n??Mesa = ""
                                    },
                                    placeholder = { Text("N??Comensales") },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                    label = { Text(text = "N??Comensales") },
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 20.dp)
                                )
                            }
                        }
                    }
                )
            },
            content = {
                val scrollState = rememberScrollState()
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    if (refreshTables) {
                        filterByAllFilters(
                            allTables = allTables,
                            n??MesasFilter = n??Mesa,
                            zoneFilter = textSelectedZone.value,
                            stateFilter = textState.value,
                            dinersFilter = n??Comensales,
                            mainViewModelCreateOrder = mainViewModelCreateOrder,
                            onValueChangeDisableAlert = onValueChangeDisableAlert,
                            onValueChangeSelectedTable = onValueChangeSelectedTable,
                            scaffoldState = scaffoldState,
                        )
                    }
                }
            },
        )
    }
}


@Composable
private fun selectedDropDownMenu(
    text: String,
    suggestions: List<String>,
    onValueChangeN??Mesa: (String) -> Unit): String
{
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
                        onValueChangeN??Mesa("")
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
    return selectedText
}

private fun checkState(
    state: String,
    reservation: Reservation
): Color {
    if (!reservation._id.equals(0)) return Color.Yellow
    when(state){
        "Libre" -> return Color(0xFF2EEE5D)
        "Ocupada" -> return Color(0xFFEE2E31)
        "Reservada" -> return Color.Yellow
        else -> {
            Color.White
        }
    }
    return Color.White
}


@ExperimentalFoundationApi
@Composable
private fun filterByAllFilters(
    allTables: List<Mesas>,
    n??MesasFilter: String,
    zoneFilter: String,
    stateFilter: String,
    dinersFilter: String,
    mainViewModelCreateOrder : MainViewModelCreateOrder,
    onValueChangeDisableAlert: (Boolean) -> Unit,
    onValueChangeSelectedTable: (Mesas) -> Unit,
    scaffoldState: ScaffoldState,
) {

    //Obtener todas las mesas (Luego le vamos restando las que no cumplan la condici??n)
    val allFilterTables: MutableList<Mesas> = mutableListOf()
    val removeTables: MutableList<Mesas> = mutableListOf()
    allTables.forEach { allFilterTables.add(it) }
    var listOfAllFilterTables: List<Mesas> = mutableListOf()
    allFilterTables.sortedBy {it.number}

    if (!n??MesasFilter.equals("")) {
        allFilterTables.forEach{
            if (it.number.equals(n??MesasFilter.toInt())) listOfAllFilterTables = listOf(it)
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


        createTables(
            allFilterTables = listOfAllFilterTables,
            mainViewModelCreateOrder = mainViewModelCreateOrder,
            onValueChangeDisableAlert = onValueChangeDisableAlert,
            onValueChangeSelectedTable = onValueChangeSelectedTable,
            scaffoldState = scaffoldState,
        )

}

@ExperimentalFoundationApi
@Composable
private fun createTables(
    allFilterTables: List<Mesas>,
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    onValueChangeDisableAlert:  (Boolean) -> Unit,
    onValueChangeSelectedTable: (Mesas) -> Unit,
    scaffoldState: ScaffoldState
) {
    var showMenu by remember { mutableStateOf(false) }
    val tableOptions = remember { mutableStateOf(false) }
    val reservation = remember { mutableStateOf(false) }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp, bottom = 30.dp, top = 20.dp)
    ) {
        for (i in allFilterTables) {

            item {
                printTable(
                    mainViewModelCreateOrder = mainViewModelCreateOrder,
                    onValueChangeDisableAlert = onValueChangeDisableAlert,
                    onValueChangeSelectedTable = onValueChangeSelectedTable,
                    table = i,
                    scaffoldState = scaffoldState,
                    reservation = isReserver(table = i, mainViewModelCreateOrder = mainViewModelCreateOrder)

                )
            }
        }
    }
}

private fun isReserver(
    table: Mesas,
    mainViewModelCreateOrder: MainViewModelCreateOrder
): Reservation{
    mainViewModelCreateOrder.reservationListResponse.forEach {
        if(it.idTable.equals(table._id)) {
            return it
        }
    }
    return Reservation(0,"",0,0,0,0,0,0,0,0,)
}

@Composable
private fun printTable(
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    onValueChangeDisableAlert:  (Boolean) -> Unit,
    onValueChangeSelectedTable: (Mesas) -> Unit,
    table: Mesas,
    scaffoldState: ScaffoldState,
    reservation: Reservation
) {
    val current = LocalContext.current
    val scope = rememberCoroutineScope()

    Card(
        Modifier
            .pointerInput(Unit) {

                detectTapGestures(
                    onLongPress = { Offset ->
                        onValueChangeSelectedTable(table)
                        scope.launch {
                            when (
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Cambiar estado de la mesa",
                                    actionLabel = "Ok"
                                )
                            ) {
                                SnackbarResult.Dismissed ->
                                    Log.d("Track", "Dismissed")
                                SnackbarResult.ActionPerformed ->
                                    Log.d("Track", "Action!")
                            }
                        }
                    },
                    onTap = { Offset ->
                        onValueChangeSelectedTable(table)
                        mainViewModelCreateOrder.getOrderByTableWithDelay(id = table._id) {
                            if (it) {
                                mainViewModelCreateOrder.editOrder = true
                                mainViewModelCreateOrder.lineasPedidos = arrayListOf()
                                mainViewModelCreateOrder.pedido =
                                    mainViewModelCreateOrder.orderByTable
                                mainViewModelCreateOrder.lineasPedidos =
                                    mainViewModelCreateOrder.orderByTable.lineasPedido
                                navController.navigate("${Destinations.CreateOrderWithOrder.route}/${table._id}")
                            } else {
                                onValueChangeDisableAlert(true)
                            }
                        }
                    }
                )
            }
            .padding(PaddingValues(10.dp)),
            backgroundColor = checkState(table.state, reservation),
            elevation = 6.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "${table.zone} (${table.numChair})",
                fontSize = 10.sp,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(2.dp))

            Text(
                text = table.number.toString(),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
            if (!reservation._id.equals(0)) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = reservation.name,
                    fontSize = 10.sp,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(2.dp))

            }
            else{
                Spacer(modifier = Modifier.padding(11.dp))
            }
        }
    }
}


@Composable
private fun confirmCreateOrder(
    table: Mesas,
    onValueChangeDisableAlert: (Boolean) -> Unit,
    mainViewModelCreateOrder: MainViewModelCreateOrder
) {
    MaterialTheme {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "??Seguro que desea crear un pedido?")
                },
                text = {
                    Text("")
                },
                confirmButton = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                mainViewModelCreateOrder.pedido = Pedidos(idMesa = table._id,lineasPedido = arrayListOf(),_id = 0)
                                mainViewModelCreateOrder.uploadOrder(order = mainViewModelCreateOrder.pedido) {
                                    mainViewModelCreateOrder.deleteOrder(
                                        id = mainViewModelCreateOrder.pedido._id,
                                        idMesa = mainViewModelCreateOrder.pedido.idMesa
                                    )
                                }

                                mainViewModelCreateOrder.editOrder = false

                                mainViewModelCreateOrder.lineasPedidos = arrayListOf()
                                mainViewModelCreateOrder.getOrderByTable(id = table._id){
                                    mainViewModelCreateOrder.pedido = mainViewModelCreateOrder.orderByTable
                                }

                                navController.navigate("${Destinations.CreateOrderWithOrder.route}/${table._id}")

                            },
                            Modifier.align(Alignment.Start)
                        ) {
                            Text("Aceptar")
                        }
                    }

                },
                dismissButton = {
                    Button(
                        onClick = {
                            onValueChangeDisableAlert(false)
                        },
                        Modifier.align(Alignment.End)

                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MySnackHost(
    state: SnackbarHostState,
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    selectedTable: Mesas,
    onValueChangeRefreshTables: (Boolean) -> Unit,
    onValueChangeSelectedTable: (Mesas) -> Unit,
) {
    var current = LocalContext.current
    val scope = rememberCoroutineScope()

    SnackbarHost(
        hostState = state,
        snackbar = { snackbarData: SnackbarData ->
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = snackbarData.message)
                        Spacer(modifier = Modifier.padding(start = 90.dp))
                        Button(
                            onClick = {
                                //Ejecutar
                                var editMesa = Mesas(
                                    _id = selectedTable._id,
                                    zone = selectedTable.zone,
                                    state = "Ocupada",
                                    numChair = selectedTable.numChair,
                                    number = selectedTable.number
                                )

                                mainViewModelCreateOrder.editMesa(editMesa){
                                    mainViewModelCreateOrder.getMesaList {
                                        onValueChangeRefreshTables(false)
                                        onValueChangeRefreshTables(true)
                                    }

                                }

                            }
                        ) {
                            Text(text = snackbarData.actionLabel.toString())
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun reloadTables(
    mainViewModelCreateOrder: MainViewModelCreateOrder
) {
    LaunchedEffect(key1 = true) {
        delay(4000)
       mainViewModelCreateOrder.getMesaList {  }
    }
}






@Preview(showBackground = true)
@ExperimentalFoundationApi
@Composable
fun DefaultPreview3() {
    //MainAccessToTables()
}