package sainero.dani.intermodular.Views.Administration.Employee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.launch
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.Utils.SearchWidgetState


@ExperimentalComposeUiApi
@Composable
fun MainEmployeeManager(
    mainViewModelSearchBar: MainViewModelSearchBar,
    mainViewModelEmployee: MainViewModelEmployee
) {

    var clearSearchBar = remember { mutableStateOf(true) }
    if (clearSearchBar.value) {
        mainViewModelSearchBar.clearSearchBar()
        clearSearchBar.value = false
    }

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val searchWidgetState by mainViewModelSearchBar.searchWidgetState
    val searchTextState by mainViewModelSearchBar.searchTextState
    val aplicateFilter = remember { mutableStateOf(false) }
    var filter: String = ""
    var allUsers: List<Users> = mainViewModelEmployee.userListResponse



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Destinations.NewEmployee.route)
                    },
                    backgroundColor = MaterialTheme.colors.primary

                ) {
                    Text("+")
                }
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    aplicateFilter.value = true
                    if (aplicateFilter.value)  filterContentByName(allUsers = allUsers, filterName = filter)
                }
            }
        )
    }
}

@Composable
private fun filterContentByName(allUsers: List<Users>,filterName: String) {
    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp),

    ) {
        item {
            for (i in allUsers) {
                if (i.name.lowercase().contains(filterName)) {

                    Card(
                        modifier = Modifier
                            .padding(8.dp, 4.dp)
                            .fillMaxWidth()
                            .height(90.dp)
                            .clickable {
                                navController.navigate("${Destinations.EditEmployee.route}/${i._id}")
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
                                    data = if (i.rol.equals("Administrador")) R.drawable.administrador else R.drawable.empleado,
                                    builder = {
                                        scale(Scale.FILL)
                                        //placeholder(R.drawable.notification_action_background)
                                        transformations(CircleCropTransformation())
                                    },
                                ),
                                contentDescription = "Im??gen del empleado ${i.name}",
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
                                    text = i.dni,
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
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    val expanded = remember { mutableStateOf(false)}

    TopAppBar(
        title = {
            Text(text = "Lista de Empleados",color = Color.White)
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
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation//MaterialTheme.colors.primary
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




@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
   // MainEmployeeManager(mainViewModelSearchBar: MainViewModelSearchBar)
}