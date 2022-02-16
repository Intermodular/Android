package sainero.dani.intermodular.Navigation


import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sainero.dani.intermodular.DataClass.Tipos
import sainero.dani.intermodular.Utils.DragAndDrop.MainDragAndDrop
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
import sainero.dani.intermodular.Views.Administration.Products.Especifications.MainEspecifications
import sainero.dani.intermodular.Views.Administration.Products.Especifications.MainViewModelEspecifications
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.MainIngredient
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.MainViewModelIngredients
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainExtras
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainNewExtra
import sainero.dani.intermodular.Views.Administration.Products.Types.Extras.MainViewModelExtras
import sainero.dani.intermodular.Views.Administration.Products.Types.MainProductEditType
import sainero.dani.intermodular.Views.Administration.Products.Types.MainProductNewType
import sainero.dani.intermodular.Views.Administration.Products.Types.MainProductTypeManager
import sainero.dani.intermodular.Views.Administration.Zone.MainEditZone
import sainero.dani.intermodular.Views.Administration.Zone.MainNewZone
import sainero.dani.intermodular.Views.Administration.Zone.MainZoneManager
import sainero.dani.intermodular.Views.Administration.Zone.Table.MainEditTable
import sainero.dani.intermodular.Views.Administration.Zone.Table.MainNewTable
import sainero.dani.intermodular.Views.Administration.Zone.Table.MainTableManager
import sainero.dani.intermodular.Views.Cobrador.CreateOrder.*
import sainero.dani.intermodular.Views.Cobrador.MainProductInformation

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
    mainViewModelIngredients: MainViewModelIngredients
){
    navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Login.route
    ){

        composable(route = Destinations.Login.route){
            LoginMain(viewModelUsers)
        }

        composable(route = Destinations.MainAdministrationActivity.route){
            MainAdministrationActivityView()
        }
        
        composable(route = Destinations.AccessToTables.route) {
            viewModelMesas.getMesaList()
            viewModelZonas.getZoneList()
            MainAccessToTables(viewModelMesas = viewModelMesas, viewModelZonas = viewModelZonas,mainViewModelCreateOrder = mainViewModelCreateOrder)
        }
        
        composable(route = Destinations.EmployeeManager.route) {
            viewModelUsers.getUserList()
            MainEmployeeManager(mainViewModelSearchBar = mainViewModelSearchBar, viewModelUsers = viewModelUsers)
        }
        
        composable(route = Destinations.ProductTypeManager.route) {
            viewModelTipos.getTypesList()
            MainProductTypeManager(mainViewModelSearchBar = mainViewModelSearchBar, viewModelTipos = viewModelTipos, mainViewModelExtras = mainViewModelExtras)
        }
        
        composable(route = Destinations.ZoneManager.route) {
            viewModelZonas.getZoneList()
            MainZoneManager(mainViewModelSearchBar = mainViewModelSearchBar, viewModelZonas = viewModelZonas)
        }
        


        composable(
            route = "${Destinations.EditEmployee.route}/{employeeId}",
            arguments = listOf(navArgument("employeeId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("employeeId")
            requireNotNull(id)

            viewModelUsers.getUserList()
            MainEditEmployee(id,viewModelUsers)
        }

        
        composable(route = Destinations.ProductManager.route) {
            viewModelProductos.getProductList()
            viewModelTipos.getTypesList()
            MainProductManager(
                mainViewModelSearchBar = mainViewModelSearchBar,
                viewModelProductos = viewModelProductos,
                mainViewModelEspecifications = mainViewModelEspecifications,
                mainViewModelIngredients = mainViewModelIngredients
            )
        }

        composable(
            route = "${Destinations.EditProduct.route}/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("productId")
            requireNotNull(id)
            viewModelProductos.getProductList()
            viewModelProductos.getProductById(id)
            MainEditProduct(
                id = id,
                viewModelProductos =  viewModelProductos,
                viewModelTipos = viewModelTipos,
                mainViewModelEspecifications = mainViewModelEspecifications,
                mainViewModelIngredients = mainViewModelIngredients
            )
        }

        composable(route = Destinations.NewEmployee.route) {
            MainNewEmployee(viewModelUsers)
        }

        composable(route = Destinations.NewProduct.route) {
            viewModelTipos.getTypesList()
            MainNewProduct(
                viewModelProductos = viewModelProductos,
                viewModelTipos = viewModelTipos,
                mainViewModelEspecifications = mainViewModelEspecifications,
                mainViewModelIngredients = mainViewModelIngredients
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
            route = "${Destinations.EditZone.route}/{zoneId}",
            arguments = listOf(navArgument("zoneId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("zoneId")
            requireNotNull(id)
            viewModelZonas.getZoneList()
            MainEditZone(id,viewModelZonas)
        }

        composable(route = Destinations.NewZone.route) {
            MainNewZone(viewModelZonas)
        }

        composable(
            route = "${Destinations.ProductEditType.route}/{typeId}",
            arguments = listOf(
                navArgument("typeId"){type = NavType.IntType},
            )
        ) {
            val id = it.arguments?.getInt("typeId")
            requireNotNull(id)
            viewModelTipos.getTypesList()
            viewModelExtras.getExtrasList()
            MainProductEditType(id = id,viewModelTipos = viewModelTipos,mainViewModelExtras = mainViewModelExtras)
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
                viewModelProductos, mainViewModelEspecifications = mainViewModelEspecifications
            )
        }

        composable(
            route = "${Destinations.ProductNewType.route}",
        ) {
            MainProductNewType(viewModelTipos =  viewModelTipos, mainViewModelExtras= mainViewModelExtras)
        }

        composable(route = Destinations.TableManager.route){
            viewModelMesas.getMesaList()
            MainTableManager(mainViewModelSearchBar = mainViewModelSearchBar, viewModelMesas = viewModelMesas)
        }

        composable(
            route = "${Destinations.EditTable.route}/{tableId}",
            arguments = listOf(navArgument("tableId"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("tableId")
            requireNotNull(id)
            viewModelMesas.getMesaList()
            MainEditTable(_id = id, viewModelMesas = viewModelMesas)
        }

        composable(route = Destinations.NewTable.route){
            viewModelMesas.getMesaList()
            MainNewTable(viewModelMesas = viewModelMesas)
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
            route = "${Destinations.NewExtras.route}/{productId}",
            arguments = listOf(navArgument("productId"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("productId")
            requireNotNull(id)
            viewModelTipos.getTypesList()
            MainNewExtra(mainViewModelExtras)
        }


        composable(
            route = "${Destinations.NewExtras.route}/{productId}",
            arguments = listOf(navArgument("productId"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("productId")
            requireNotNull(id)
            viewModelTipos.getTypesList()
            MainNewExtra(mainViewModelExtras)
        }

        composable(
            route = "${Destinations.EditOrder.route}/{tableId}",
            arguments = listOf(navArgument("tableId"){type = NavType.IntType})

        ){
            val id = it.arguments?.getInt("tableId")
            requireNotNull(id)
            MainEditOrder(
                mainViewModelCreateOrder = mainViewModelCreateOrder,
                tableId = id,
                viemModelPedidos = viewModelPedidos
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
            route = Destinations.CreateOrder.route + "/{tableId}",
            arguments = listOf(navArgument("tableId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("tableId")
            requireNotNull(id,{"La id de la mesa no puede ser nula"})

            viewModelMesas.getMesaList()
            viewModelProductos.getProductList()
            viewModelTipos.getTypesList()
            MainCreateOrder(
                tableId = id,
                viewModelProductos = viewModelProductos,
                viewModelTipos = viewModelTipos,
                viewModelMesas = viewModelMesas,
                viewModelPedidos = viewModelPedidos,
                mainViewModelCreateOrder = mainViewModelCreateOrder
            )
        }

        composable(
            route = Destinations.CreateOrderWithOrder.route + "/{tableId}",
            arguments = listOf(navArgument("tableId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("tableId")
            requireNotNull(id,{"La id de la mesa no puede ser nula"})

            viewModelMesas.getMesaList()
            viewModelProductos.getProductList()
            viewModelTipos.getTypesList()
            MainCreateOrderWithOrder(
                tableId = id,
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

        composable(route = "lol") {
            MainFunDragAndDrop()
        }
    }
}
