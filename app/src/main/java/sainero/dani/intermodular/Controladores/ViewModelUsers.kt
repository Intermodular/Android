package sainero.dani.intermodular.Controladores

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiService
import sainero.dani.intermodular.DataClass.Users

class ViewModelUsers: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


//Métodos get
    var userListResponse: List <Users> by mutableStateOf ( listOf ())
    fun getUserList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val usersList = apiService.getUsers()
                userListResponse = usersList

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

     var user: Users by mutableStateOf(Users(0,"error","","","","","","","",""))

    fun getUserById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                user = apiService.getUserById(id)
                //user = userById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var newUser: Users by mutableStateOf(Users(0,"error","","","","","","","",""))
    fun uploadUser() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                newUser = apiService.uploadUser()


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var editUser: Users by mutableStateOf(Users(0,"error","","","","","","","",""))
    fun editUser() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                editUser = apiService.editUser()


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    var deleteUser: Users by mutableStateOf(Users(0,"error","","","","","","","",""))
    fun deleteUser(id: Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                deleteUser = apiService.deleteUser(id)


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
