package sainero.dani.intermodular.Views.Administration.Products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.Utils.SearchWidgetState
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.Views.Administration.Products.ui.theme.IntermodularTheme


@ExperimentalFoundationApi
class ProductManager : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainProductManager(mainViewModelSearchBar: MainViewModelSearchBar,viewModelProductos: ViewModelProductos) {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    var allProducts: List<Productos> = viewModelProductos.productListResponse
    val scope = rememberCoroutineScope()


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

                filterContentByName(
                    allProducts = allProducts,
                    filterName = filter
                )


            }
        },
        //Preguntar sobre si quieren agregar un producto así
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    GlobalVariables.navController.navigate(Destinations.NewProduct.route)
                }
            ) {
                Text("+")
            }
        }
    )
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
@ExperimentalFoundationApi
private fun creteProductList(listProducts: MutableList<Productos>) {
    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ){
        for( i in listProducts) {
            item {
                Row (
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable {
                            GlobalVariables.navController.navigate("${Destinations.EditProduct.route}/${i._id}")
                        }) {
                    Text(text = i.name)
                }
            }
        }
    }
}

@Composable
private fun filterContentByName(allProducts: List<Productos>, filterName: String) {
    LazyColumn(
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp)
    ) {

        for (i in allProducts) {
            if (i.name.contains(filterName)) {

                item {

                    Row (
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                navController.navigate("${Destinations.EditProduct.route}/${i._id}")
                            }) {

                        Text(text = i.name)
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(text = i.type)

                    }
                }
            }
        }
    }
}



@Composable
private fun DefaultAppBar(onSearchClicked: () -> Unit) {
    val expanded = remember { mutableStateOf(false)}

    TopAppBar(
        title = {
            Text(text = "Lista de Productos",color = Color.White)
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
                        contentDescription = "Localized description"
                    )
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded.value = false
                            navController.navigate("${Destinations.ProductTypeManager.route}")

                        }
                    ) {
                        Text(text = "Gestionar tipos")
                    }
                }
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










@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview10() {
    IntermodularTheme {
      //  MainProductManager()
    }
}
