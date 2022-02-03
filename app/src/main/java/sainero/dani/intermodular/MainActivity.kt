package sainero.dani.intermodular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import sainero.dani.intermodular.Api.MainViewModel
import sainero.dani.intermodular.ViewModels.*
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.MainViewModelSearchBar

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val mainViewModelSearchBar: MainViewModelSearchBar by viewModels()

    private val viewModelExtras by viewModels<ViewModelExtras>()
    private val viewModelMesas by viewModels<ViewModelMesas>()
    private val viewModelNominas by viewModels<ViewModelNominas>()
    private val viewModelPedidos by viewModels<ViewModelPedidos>()
    private val viewModelProductos by viewModels<ViewModelProductos>()
    private val viewModelTipos by viewModels<ViewModelTipos>()
    private val viewModelUsers by viewModels<ViewModelUsers>()
    private val viewModelZonas by viewModels<ViewModelZonas>()


    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            viewModelUsers.getUserList()
            viewModelProductos.getProductList()
            viewModelZonas.getZoneList()
            NavigationHost(mainViewModelSearchBar, viewModelUsers,viewModelExtras,viewModelMesas,viewModelNominas,viewModelPedidos,viewModelProductos,viewModelTipos,viewModelZonas)
        }
    }
}
