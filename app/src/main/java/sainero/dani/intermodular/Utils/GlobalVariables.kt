package sainero.dani.intermodular.Utils

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import sainero.dani.intermodular.DataClass.Users


class GlobalVariables {
     companion object {
         lateinit var navController: NavHostController
         var currentValidateUser: Users = Users(0, address = "", dni = "", email = "", fnac = "", name = "", newUser = false, password = "", phoneNumber = "", rol = "", surname = "", user = "")
         var textRows: MutableList<String> = mutableListOf()
         var userListResponse: List <Users> by mutableStateOf ( listOf ())
         lateinit var test: String

     }
}