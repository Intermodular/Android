package sainero.dani.intermodular.Views.Administration.Employee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sainero.dani.intermodular.Api.MainViewModel
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.Utils.SearchWidgetState

@ExperimentalFoundationApi
class EmployeeManager : ComponentActivity() {
    private val mainViewModel by  viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            IntermodularTheme {

            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MainEmployeeManager(mainViewModelSearchBar: MainViewModelSearchBar, viewModelUsers: ViewModelUsers) {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var selectedUser: Users = Users(0,"","","","","","","","admin","")
    var visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current
    val context = LocalContext.current

    val expanded = remember { mutableStateOf(false)}
    val result = remember { mutableStateOf("") }
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val searchWidgetState by mainViewModelSearchBar.searchWidgetState
    val searchTextState by mainViewModelSearchBar.searchTextState

    val aplicateFilter = remember { mutableStateOf(false) }
    var filter: String = ""

    var allUsers: List<Users> = viewModelUsers.userListResponse



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
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Destinations.NewEmployee.route)
                    }
                ) {
                    Text("+")
                }
            },
            content = {


                aplicateFilter.value = true
                if (aplicateFilter.value)  filterContentByName(allUsers = allUsers, filterName = filter)

            }

        )

    }
}

@Composable
private fun filterContentByName(allUsers: List<Users>,filterName: String) {
    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {

        for (i in allUsers) {
            if (i.name.contains(filterName)) {

                item {

                    Row (
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                navController.navigate("${Destinations.EditEmployee.route}/${i._id}")
                            }) {

                        Text(text = i.name)
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(text = i.dni)
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(text = i.rol)
                    }
                }
            }
        }
    }

}



@Composable
fun MainAppBar(
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
            Box (Modifier.wrapContentSize()){
                IconButton(onClick = {
                    expanded.value = true

                }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "Localized description",
                        tint = Color.Red
                    )
                }

                DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                    DropdownMenuItem(
                        onClick = {
                            expanded.value = false

                        }) {
                        Text(text = "Gestionar todas las nóminas")
                    }
                    Divider()
                    DropdownMenuItem(
                        onClick = {
                            expanded.value = false
                        }) {
                        Text(text = "Gestionar nomina del empleado seleccionado")
                    }
                }
            }

        },
        backgroundColor = Color.Blue
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




@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
   // MainEmployeeManager(mainViewModelSearchBar: MainViewModelSearchBar)
}