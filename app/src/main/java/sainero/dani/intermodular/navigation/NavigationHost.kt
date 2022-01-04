package sainero.dani.intermodular.navigation


import android.telecom.Call
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.Views.*
import sainero.dani.intermodular.Views.Administration.*
import sainero.dani.intermodular.Views.Cobrador.*

@ExperimentalFoundationApi
@Composable
fun NavigationHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.Login.route){

        composable(route = Destinations.Login.route){
            LoginMain(navController = navController)
        }

        composable(route = Destinations.MainAdministrationActivity.route){
            MainAdministrationActivityView(navController = navController)
        }
        
        composable(route = Destinations.AccessToTables.route) {

            MainAccessToTables(navController = navController)
        }
        
        composable(route = Destinations.Employeeanager.route) {
            MainEmployeeManager(navController = navController)
        }
        
        composable(route = Destinations.ProductTypeAdministration.route) {
            MainProductTypeAdministration(navController = navController)
        }
        
        composable(route = Destinations.ZoneAdministration.route) {
            MainZoneAdministration(navController = navController)
        }
        
        composable(
            route = Destinations.CreateOrder.route + "/{tableId}",
            arguments = listOf(navArgument("tableId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("tableId")
            requireNotNull(id,{"La id de la mesa no puede ser nulo"})

            MainCreateOrder(navController = navController,id)
        }

    }
}
