package sainero.dani.intermodular.Views.Administration.Zone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Zonas
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.Utils.SearchWidgetState
import sainero.dani.intermodular.ViewModels.ViewModelZonas

@Composable
fun MainZoneManager(
    mainViewModelSearchBar: MainViewModelSearchBar,
    mainViewModelZone: MainViewModelZone
) {

    var clearSearchBar = remember { mutableStateOf(true) }
    if (clearSearchBar.value) {
        mainViewModelSearchBar.clearSearchBar()
        clearSearchBar.value = false
    }


    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))

    //Consulta BD
    var allZones: List<Zonas> = mainViewModelZone.zonesListResponse

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
                    filter = it.lowercase()
                    aplicateFilter.value = true
                },
                onCloseClicked = {
                    mainViewModelSearchBar.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    aplicateFilter.value = false
                    filter = it.lowercase()
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
                filterContentByName(
                    allZones = allZones,
                    filterName = filter
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                   navController.navigate(Destinations.NewZone.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Text("+")
            }
        }
    )
}

@Composable
private fun filterContentByName(allZones: List<Zonas>, filterName: String) {
    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {
        for (i in allZones) {
            if (i.name.lowercase().contains(filterName)) {
                item {
                    Card(modifier = Modifier
                        .padding(8.dp, 4.dp)
                        .fillMaxWidth()
                        .height(90.dp)
                        .clickable {
                            navController.navigate("${Destinations.EditZone.route}/${i._id}")
                        },
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxSize(),
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = R.drawable.default_admin_img,
                                    builder = {
                                        scale(Scale.FILL)
                                        //placeholder(R.drawable.notification_action_background)
                                        transformations(CircleCropTransformation())
                                    },
                                ),
                                contentDescription = "Imágen de zona ${i.name}",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(0.2f),
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Column(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxHeight()
                                    .weight(0.8f),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "${i.name}",
                                    style = MaterialTheme.typography.subtitle1,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = "Número de mesas: ${i.nºTables}",
                                    style = MaterialTheme.typography.caption,
                                    modifier = Modifier
                                        .padding(4.dp),
                                )
                            }
                        }
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
            Text(text = "Lista de Zona",color = Color.White)
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
            Box (Modifier.wrapContentSize()){
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
                    onDismissRequest = { expanded.value = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate(Destinations.TableManager.route)
                            expanded.value = false
                        }
                    ) {
                        Text(text = "Gestionar Mesas")
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
        color = Color.Blue
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
                backgroundColor = MaterialTheme.colors.primary,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    IntermodularTheme {
        //MainZoneManager(mainViewModelSearchBar = "")
    }
}