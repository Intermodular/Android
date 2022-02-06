package sainero.dani.intermodular.Views.Administration.Zone.Table

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Zonas
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Utils.SearchWidgetState
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.ViewModels.ViewModelZonas

class TableManager : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainTableManager(mainViewModelSearchBar: MainViewModelSearchBar,viewModelMesas: ViewModelMesas){
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    //Consulta BD
    var allMesas: List<Mesas> = viewModelMesas.mesasListResponse

    val searchWidgetState by mainViewModelSearchBar.searchWidgetState
    val searchTextState by mainViewModelSearchBar.searchTextState

    val aplicateFilter = remember { mutableStateOf(false) }
    var filter: String = ""

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {

            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    mainViewModelSearchBar.updateSearchTextState(newValue = it)
                    aplicateFilter.value = false
                    filter = it
                    aplicateFilter.value = true
                },
                onCloseClicked = {
                    mainViewModelSearchBar.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    aplicateFilter.value = false
                    filter = it
                    aplicateFilter.value = true
                },
                onSearchTriggered = {
                    mainViewModelSearchBar.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }

            )

        },
        content = {

            aplicateFilter.value = true
            if (aplicateFilter.value) {

                filtercontentbyname(
                    allMesas = allMesas,
                    filterName = filter
                )


            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Destinations.NewTable.route)
                }
            ) {
                Text("+")
            }
        }
    )
}


@Composable
fun filtercontentbyname(allMesas: List<Mesas>, filterName: String){
    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp , end = 30.dp)
    ) {

        for (i in allMesas) {
            if (i.zone.contains(filterName)) {
                item {
                    Row (
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                navController.navigate("${Destinations.EditTable.route}/${i._id}")
                            }
                    ) {
                        Text(text = i.zone)
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(text = i.numChair.toString())
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(text = i.state.toString())
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(text = i.number.toString())
                    }
                }
            }
        }
    }
}

@Composable
private fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(onSearchClicked = onSearchTriggered)
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}


@Composable
private fun DefaultAppBar(onSearchClicked: () -> Unit) {
    val expanded = remember { mutableStateOf(false)}

    TopAppBar(
        title = {
            Text(text = "Lista de Mesa",color = Color.White)
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color.Blue
    )
}


@Composable
private fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.Blue//MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search by name...",
                    color = Color.White
                )
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                color = Color.White

            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview16() {

}