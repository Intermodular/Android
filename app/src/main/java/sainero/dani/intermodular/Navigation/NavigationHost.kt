package sainero.dani.intermodular.Navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sainero.dani.intermodular.Utils.DragAndDrop.MainFunDragAndDrop
import sainero.dani.intermodular.Views.*
import sainero.dani.intermodular.Views.Administration.Employee.MainEditEmployee
import sainero.dani.intermodular.Views.Administration.Employee.MainEmployeeManager
import sainero.dani.intermodular.Views.Administration.Employee.MainNewEmployee
import sainero.dani.intermodular.Views.Administration.MainAdministrationActivityView
import sainero.dani.intermodular.Views.Administration.Products.MainEditProduct
import sainero.dani.intermodular.Views.Administration.Products.MainNewProduct
import sainero.dani.intermodular.Views.Administration.Products.MainProductManager
import sainero.dani.intermodular.Views.Cobrador.MainAccessToTables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.ViewModels.*
import sainero.dani.intermodular.Views.Administration.Employee.MainViewModelEmployee
import sainero.dani.intermodular.Views.Administration.Products.Especifications.MainEspecifications
import sainero.dani.intermodular.Views.Administration.Products.Especifications.MainViewModelEspecifications
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.MainIngredient
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.MainViewModelIngredients
import sainero.dani.intermodular.Views.Administration.Products.MainViewModelProducts
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainExtras
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainNewExtra
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainViewModelExtras
import sainero.dani.intermodular.Views.Administration.Products.Types.MainProductEditType
import sainero.dani.intermodular.Views.Administration.Products.Types.MainProductNewType
import sainero.dani.intermodular.Views.Administration.Products.Types.MainProductTypeManager
import sainero.dani.intermodular.Views.Administration.Products.Types.MainViewModelTypes
import sainero.dani.intermodular.Views.Administration.Zone.MainEditZone
import sainero.dani.intermodular.Views.Administration.Zone.MainNewZone
import sainero.dani.intermodular.Views.Administration.Zone.MainViewModelZone
import sainero.dani.intermodular.Views.Administration.Zone.MainZoneManager
import sainero.dani.intermodular.Views.Administration.Zone.Table.MainEditTable
import sainero.dani.intermodular.Views.Administration.Zone.Table.MainNewTable
import sainero.dani.intermodular.Views.Administration.Zone.Table.MainTableManager
import sainero.dani.intermodular.Views.Administration.Zone.Table.MainViewModelTable
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.*
import sainero.dani.intermodular.Views.Cobrador.MainProductInformation
import sainero.dani.intermodular.Views.Login.MainViewModelLogin

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun NavigationHost(
    mainViewModelSearchBar: MainViewModelSearchBar,
    viewModelUsers: ViewModelUsers,
    viewModelExtras: ViewModelExtras,
    viewModelMesas: ViewModelMesas,
    viewModelNominas: ViewModelNominas,
    viewModelPedidos: ViewModelPedidos,
    viewModelProductos: ViewModelProductos,
    viewModelTipos: ViewModelTipos,
    viewModelZonas: ViewModelZonas,
    mainViewModelCreateOrder: MainViewModelCreateOrder,
    mainViewModelExtras: MainViewModelExtras,
    mainViewModelEspecifications: MainViewModelEspecifications,
    mainViewModelIngredients: MainViewModelIngredients,
    mainViewModelZone: MainViewModelZone,
    mainViewModelEmployee: MainViewModelEmployee,
    mainViewModelTable: MainViewModelTable,
    mainViewModelTypes: MainViewModelTypes,
    mainViewModelProductos: MainViewModelProducts,
    mainViewModelLogin: MainViewModelLogin
){
    navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Login.route
    ){

        composable(route = Destinations.Login.route){
            LoginMain(mainViewModelLogin = mainViewModelLogin)
        }

        //Administration
        composable(route = Destinations.MainAdministrationActivity.route){
            MainAdministrationActivityView()
        }

            //Tables
        composable(
            route = "${Destinations.EditTable.route}/{tableId}",
            arguments = listOf(navArgument("tableId"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("tableId")
            requireNotNull(id)
            mainViewModelTable.getZoneList()
            mainViewModelTable.getMesaList()
            MainEditTable(
                _id = id,
                mainViewModelTable = mainViewModelTable
            )
        }

        composable(route = Destinations.NewTable.route){
            mainViewModelTable.getMesaList()
            mainViewModelTable.getZoneList()
            MainNewTable(
                mainViewModelTable = mainViewModelTable
            )
        }

        composable(route = Destinations.TableManager.route){
            mainViewModelTable.getMesaList()
            MainTableManager(
                mainViewModelSearchBar = mainViewModelSearchBar,
                mainViewModelTable = mainViewModelTable
            )
        }

            //Employees
        composable(route = Destinations.EmployeeManager.route) {
            mainViewModelEmployee.getUserList()
            MainEmployeeManager(
                mainViewModelSearchBar = mainViewModelSearchBar,
                mainViewModelEmployee = mainViewModelEmployee
            )
        }

        composable(
            route = "${Destinations.EditEmployee.route}/{employeeId}",
            arguments = listOf(navArgument("employeeId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("employeeId")
            requireNotNull(id)
            mainViewModelEmployee.getUserList()
            MainEditEmployee(
                _id = id,
                mainViewModelEmployee = mainViewModelEmployee
            )
        }

        composable(route = Destinations.NewEmployee.route) {
            MainNewEmployee(mainViewModelEmployee = mainViewModelEmployee)
        }

            //Types
        composable(route = Destinations.ProductTypeManager.route) {
            mainViewModelTypes.getTypesList()
            MainProductTypeManager(
                mainViewModelSearchBar = mainViewModelSearchBar,
                mainViewModelExtras = mainViewModelExtras,
                mainViewModelTypes = mainViewModelTypes
            )
        }

        composable(
            route = "${Destinations.ProductNewType.route}",
        ) {
            MainProductNewType(
                mainViewModelExtras = mainViewModelExtras,
                mainViewModelTypes = mainViewModelTypes
            )
        }

        composable(
            route = "${Destinations.ProductEditType.route}/{typeId}",
            arguments = listOf(
                navArgument("typeId"){type = NavType.IntType},
            )
        ) {
            val id = it.arguments?.getInt("typeId")
            requireNotNull(id)
            mainViewModelTypes.getTypesList()
            viewModelExtras.getExtrasList()
            MainProductEditType(
                id = id,
                mainViewModelExtras = mainViewModelExtras,
                mainViewModelTypes = mainViewModelTypes
            )
        }

        composable(
            route = "${Destinations.Extras.route}/{productId}",
            arguments = listOf(navArgument("productId"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("productId")
            requireNotNull(id)
            viewModelTipos.getTypesList()
            MainExtras(mainViewModelExtras,id)
        }

        composable(
            route = "${Destinations.NewExtras.route}",
        ){
            viewModelTipos.getTypesList()
            MainNewExtra(mainViewModelExtras)
        }

            //Zones
        composable(route = Destinations.ZoneManager.route) {
            mainViewModelZone.getZoneList()
            MainZoneManager(
                mainViewModelSearchBar = mainViewModelSearchBar,
                mainViewModelZone = mainViewModelZone
            )
        }

        composable(
            route = "${Destinations.EditZone.route}/{zoneId}",
            arguments = listOf(navArgument("zoneId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("zoneId")
            requireNotNull(id)
            mainViewModelZone.getZoneList()
            MainEditZone(
                _id = id,
                mainViewModelZone = mainViewModelZone
            )
        }

        composable(route = Destinations.NewZone.route) {
            mainViewModelZone.getZoneList()
            MainNewZone(
                mainViewModelZone = mainViewModelZone
            )
        }

            //Products
        composable(route = Destinations.ProductManager.route) {
            mainViewModelProductos.getProductList()
            mainViewModelProductos.getTypesList()
            MainProductManager(
                mainViewModelSearchBar = mainViewModelSearchBar,
                mainViewModelEspecifications = mainViewModelEspecifications,
                mainViewModelIngredients = mainViewModelIngredients,
                mainViewModelProductos = mainViewModelProductos
            )
        }

        composable(
            route = "${Destinations.EditProduct.route}/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("productId")
            requireNotNull(id)
            mainViewModelProductos.getProductList()
            mainViewModelProductos.getProductById(id)
            MainEditProduct(
                id = id,
                mainViewModelEspecifications = mainViewModelEspecifications,
                mainViewModelIngredients = mainViewModelIngredients,
                mainViewModelProductos = mainViewModelProductos
            )
        }

        composable(route = Destinations.NewProduct.route) {
            mainViewModelProductos.getTypesList()
            MainNewProduct(
                mainViewModelEspecifications = mainViewModelEspecifications,
                mainViewModelIngredients = mainViewModelIngredients,
                mainViewModelProductos = mainViewModelProductos
            )
        }

        composable(
            route = "${Destinations.Ingredient.route}/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("productId")
            requireNotNull(id)
            viewModelProductos.getProductList()
            MainIngredient(
                _id = id,
                viewModelProductos = viewModelProductos,
                mainViewModelIngredients = mainViewModelIngredients
            )
        }

        composable(
            route = "${Destinations.Especifications.route}/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("productId")
            requireNotNull(id)
            viewModelProductos.getProductById(id)
            viewModelProductos.getProductList()
            MainEspecifications(
                id = id,
                viewModelProductos =  viewModelProductos,
                mainViewModelEspecifications = mainViewModelEspecifications
            )
        }

        //Cobrador

        composable(route = Destinations.AccessToTables.route) {
            mainViewModelCreateOrder.getMesaList{}
            viewModelZonas.getZoneList()
            MainAccessToTables(
                viewModelZonas = viewModelZonas,
                mainViewModelCreateOrder = mainViewModelCreateOrder
            )
        }

        composable(
            route = "${Destinations.EditOrder.route}/{tableId}/{typeId}",
            arguments = listOf(
                navArgument("tableId"){type = NavType.IntType},
                navArgument("typeId"){type = NavType.IntType}
            )

        ){

            val tableId = it.arguments?.getInt("tableId")
            val typeId = it.arguments?.getInt("typeId")
            requireNotNull(tableId)
            requireNotNull(typeId)
            mainViewModelCreateOrder.getMesaList {}
            MainEditOrder(
                mainViewModelCreateOrder = mainViewModelCreateOrder,
                tableId = tableId,
                viemModelPedidos = viewModelPedidos,
                viewModelMesas = viewModelMesas,
                typeId = typeId
            )
        }

        composable(
            route = "${Destinations.CreateOrderLine.route}/{productId}/{typeId}/{tableId}",
            arguments = listOf(
                navArgument("productId"){type = NavType.IntType},
                navArgument("typeId"){type = NavType.IntType},
                navArgument("tableId"){type = NavType.IntType}
            )

        ){
            val productId = it.arguments?.getInt("productId")
            val typeId = it.arguments?.getInt("typeId")
            val tableId = it.arguments?.getInt("tableId")

            requireNotNull(productId)
            requireNotNull(typeId)
            requireNotNull(tableId)

            MainCreateOrderLine(
                mainViewModelCreateOrder = mainViewModelCreateOrder,
                viewModelProductos = viewModelProductos,
                viewModelTipos = viewModelTipos,
                productId = productId,
                typeId = typeId,
                tableId = tableId,
                viewModelPedidos = viewModelPedidos
            )
        }

        composable(
            route = "${Destinations.EditOrderLine.route}/{typeId}",
            arguments = listOf(navArgument("typeId"){type = NavType.IntType})
        ){
            var typeId = it.arguments?.getInt("typeId")
            requireNotNull(typeId)
            viewModelTipos.getTypesList()
            MainEditOrderLine(
                mainViewModelCreateOrder = mainViewModelCreateOrder,
                viewModelTipos = viewModelTipos,
                typeId = typeId
            )
        }

        composable(
            route = "${Destinations.CreateOrderWithOrder.route}/{tableId}",
            arguments = listOf(
                navArgument("tableId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val tableId = backStackEntry.arguments?.getInt("tableId")
            requireNotNull(tableId,{"La id de la mesa no puede ser nula"})

            viewModelMesas.getMesaList()
            viewModelProductos.getProductList()
            viewModelTipos.getTypesList()
            MainCreateOrderWithOrder(
                tableId = tableId,
                viewModelProductos = viewModelProductos,
                viewModelTipos = viewModelTipos,
                viewModelMesas = viewModelMesas,
                viewModelPedidos = viewModelPedidos,
                mainViewModelCreateOrder = mainViewModelCreateOrder
            )
        }


        composable(
            route = Destinations.ProductInformation.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("productId")
            requireNotNull(id,{"El producto no puede ser nulo"})

            viewModelProductos.getProductList()
            MainProductInformation(
                viewModelProductos = viewModelProductos,
                productId = id
            )
        }


        /////////////
        composable(route = "lol") {
            MainFunDragAndDrop()
        }
    }
}
