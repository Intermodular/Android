package sainero.dani.intermodular.Navigation


import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sainero.dani.intermodular.Api.MainViewModel
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Views.*
import sainero.dani.intermodular.Views.Administration.Employee.MainEditEmployee
import sainero.dani.intermodular.Views.Administration.Employee.MainEmployeeManager
import sainero.dani.intermodular.Views.Administration.Employee.MainNewEmployee
import sainero.dani.intermodular.Views.Administration.MainAdministrationActivityView
import sainero.dani.intermodular.Views.Administration.Products.MainEditProduct
import sainero.dani.intermodular.Views.Administration.Products.MainNewProduct
import sainero.dani.intermodular.Views.Administration.Products.MainProductManager
import sainero.dani.intermodular.Views.Cobrador.MainAccessToTables
import sainero.dani.intermodular.Views.Cobrador.MainCreateOrder
import androidx.activity.viewModels
import sainero.dani.intermodular.Controladores.ViewModelUsers
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Utils.MainViewModelSearchBar
import sainero.dani.intermodular.Views.Administration.Products.Ingredients.MainIngredient
import sainero.dani.intermodular.Views.Administration.Products.Types.MainProductTypeManager
import sainero.dani.intermodular.Views.Administration.Zone.MainEditZone
import sainero.dani.intermodular.Views.Administration.Zone.MainZoneManager

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun NavigationHost(mainViewModelSearchBar: MainViewModelSearchBar, mainViewModel: MainViewModel,viewModelUsers: ViewModelUsers){


    navController = rememberNavController()


    NavHost(navController = navController, startDestination = Destinations.Login.route){

        composable(route = Destinations.Login.route){
            LoginMain(viewModelUsers)
        }

        composable(route = Destinations.MainAdministrationActivity.route){
            MainAdministrationActivityView()
        }
        
        composable(route = Destinations.AccessToTables.route) {

            MainAccessToTables()
        }
        
        composable(route = Destinations.EmployeeManager.route) {
            MainEmployeeManager(mainViewModelSearchBar = mainViewModelSearchBar, viewModelUsers = viewModelUsers)
        }
        
        composable(route = Destinations.ProductTypeManager.route) {
            MainProductTypeManager(mainViewModelSearchBar = mainViewModelSearchBar)
        }
        
        composable(route = Destinations.ZoneManager.route) {
            MainZoneManager(mainViewModelSearchBar = mainViewModelSearchBar)
        }
        
        composable(
            route = Destinations.CreateOrder.route + "/{tableId}",
            arguments = listOf(navArgument("tableId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("tableId")
            requireNotNull(id,{"La id de la mesa no puede ser nula"})

            MainCreateOrder(id)
        }

        composable(
            route = "${Destinations.EditEmployee.route}/{employeeId}",
            arguments = listOf(navArgument("employeeId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("employeeId")
            requireNotNull(id)
            MainEditEmployee(id,viewModelUsers)
        }

        
        composable(route = Destinations.ProductManager.route) {
            MainProductManager(mainViewModelSearchBar = mainViewModelSearchBar)
        }

        composable(
            route = "${Destinations.EditProduct.route}/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.StringType})
        ) {
            val id = it.arguments?.getString("productId")
            requireNotNull(id)
            MainEditProduct(id)
        }

        composable(route = Destinations.NewEmployee.route) {
            MainNewEmployee()
        }

        composable(route = Destinations.NewProduct.route) {
            MainNewProduct()
        }

        composable(
            route = "${Destinations.Ingredient.route}/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("productId")
            requireNotNull(id)
            MainIngredient(id)
        }

        composable(
            route = "${Destinations.EditZone.route}/{zoneId}",
            arguments = listOf(navArgument("zoneId") {type = NavType.IntType})
        ) {
            val id = it.arguments?.getInt("zoneId")
            requireNotNull(id)
            MainEditZone(id)
        }
    }
}
