package sainero.dani.intermodular.Views.Login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceUser
import sainero.dani.intermodular.DataClass.Users
import java.util.regex.Pattern

class MainViewModelLogin: ViewModel() {

    //Consultas a la BD
    private var errorMessage: String by mutableStateOf ("")

    //Métodos get
    var userListResponse: List <Users> by mutableStateOf ( listOf ())
    fun getUserList(onFinishFunction: () -> Unit) {
        viewModelScope.launch {
            val apiService = ApiServiceUser.getInstance()

            try {
                val result = apiService.getUsers()
                if (result.isSuccessful) {
                    userListResponse = result.body()!!
                    onFinishFunction()
                }
                else
                    Log.d("Error: get users","Error: get users")

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos put
    var editUser: MutableList<Users> = mutableListOf()
    fun editUser(user: Users, onFinishFunction: () -> Unit) {
        viewModelScope.launch {
            val apiService = ApiServiceUser.getInstance()

            try {
                val response = apiService.editUser(user)
                apiService.getUsers()
                if (response.isSuccessful) {
                    editUser.add(response.body()!!)
                    onFinishFunction()
                }
                else
                    Log.d("Error: edit user","Error: edit user")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
//Contraseña 8 caracteres y solo caracteres alfanumeriscos y "_"

    //Validaciones
    fun isValidUser(text: String) = Pattern.compile("^[a-zA-Z0-9]+\$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidPassword(text: String) = Pattern.compile("^[a-zA-Z0-9_]{8,}\$",Pattern.CASE_INSENSITIVE).matcher(text).find()
}