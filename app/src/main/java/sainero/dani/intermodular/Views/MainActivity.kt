package sainero.dani.intermodular.Views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import sainero.dani.intermodular.ViewModels.*
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.Views.Administration.Employee.MainViewModelEmployee
import sainero.dani.intermodular.Views.Administration.Products.Especifications.MainViewModelEspecifications
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.MainViewModelIngredients
import sainero.dani.intermodular.Views.Administration.Products.MainViewModelProducts
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainViewModelExtras
import sainero.dani.intermodular.Views.Administration.Products.Types.MainViewModelTypes
import sainero.dani.intermodular.Views.Administration.Zone.MainViewModelZone
import sainero.dani.intermodular.Views.Administration.Zone.Table.MainViewModelTable
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.MainViewModelCreateOrder
import sainero.dani.intermodular.Views.Login.MainViewModelLogin
import sainero.dani.intermodular.Views.ui.theme.IntermodularTheme

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
    private val mainViewModelEspecifications by viewModels<MainViewModelEspecifications>()
    private val mainViewModelIngredients by viewModels<MainViewModelIngredients>()

    private val mainViewModelZone by viewModels<MainViewModelZone>()
    private val mainViewModelEmployee by viewModels<MainViewModelEmployee>()
    private val mainViewModelTable by viewModels<MainViewModelTable>()
    private val mainViewModelTypes by viewModels<MainViewModelTypes>()
    private val mainViewModelProductos by viewModels<MainViewModelProducts>()
    private val mainViewModelLogin by viewModels<MainViewModelLogin>()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme() {
                viewModelUsers.getUserList()
                viewModelProductos.getProductList()
                viewModelZonas.getZoneList()
                viewModelExtras.getExtrasList()
                viewModelMesas.getMesaList()
                viewModelTipos.getTypesList()
                viewModelPedidos.getOrderList()

                ///
                mainViewModelZone.getZoneList()
                mainViewModelEmployee.getUserList()
                mainViewModelTable.getMesaList()
                mainViewModelTable.getZoneList()
                mainViewModelTypes.getTypesList()
                mainViewModelProductos.getProductList()

                ////
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
                    mainViewModelModelExtras,
                    mainViewModelEspecifications,
                    mainViewModelIngredients,
                    mainViewModelZone,
                    mainViewModelEmployee,
                    mainViewModelTable,
                    mainViewModelTypes,
                    mainViewModelProductos,
                    mainViewModelLogin
                )
            }
        }
    }
}
