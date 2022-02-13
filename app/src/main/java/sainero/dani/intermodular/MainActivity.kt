package sainero.dani.intermodular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import sainero.dani.intermodular.ViewModels.*
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainViewModelExtras
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.MainViewModelCreateOrder

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val mainViewModelSearchBar: MainViewModelSearchBar by viewModels()

    private val viewModelExtras by viewModels<ViewModelExtras>()
    private val viewModelMesas by viewModels<ViewModelMesas>()
    private val viewModelNominas by viewModels<ViewModelNominas>()
    private val viewModelPedidos by viewModels<ViewModelPedidos>()
    private val viewModelProductos by viewModels<ViewModelProductos>()
    private val viewModelTipos by viewModels<ViewModelTipos>()
    private val viewModelUsers by viewModels<ViewModelUsers>()
    private val viewModelZonas by viewModels<ViewModelZonas>()
    private val mainViewModelCreateOrder by viewModels<MainViewModelCreateOrder>()
    private val mainViewModelModelExtras by viewModels<MainViewModelExtras>()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            viewModelUsers.getUserList()
            viewModelProductos.getProductList()
            viewModelZonas.getZoneList()
            viewModelExtras.getExtrasList()
            viewModelMesas.getMesaList()
            viewModelTipos.getTypesList()

            //viewModelPedidos

            NavigationHost(
                mainViewModelSearchBar,
                viewModelUsers,
                viewModelExtras,
                viewModelMesas,
                viewModelNominas,
                viewModelPedidos,
                viewModelProductos,
                viewModelTipos,
                viewModelZonas,
                mainViewModelCreateOrder,
                mainViewModelModelExtras
            )
        }
    }
}
