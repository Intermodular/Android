package sainero.dani.intermodular.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

     var user: List <Users> by mutableStateOf(listOf())

    fun getUserById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val userById = apiService.getUserById(id)
                user = userById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var newUser: Users by mutableStateOf(Users(0,"error","","","","","","","",""))
    fun uploadUser(user:Users) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                 apiService.uploadUser(/*completeUser = user, _id = user._id,user.name, user.surname, user.dni, user.phoneNumber,user.fnac,user.user, user.password,user.email*/)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //var editUser: Users by mutableStateOf(Users(0,"error","","","","","","","",""))
    fun editUser(user: Users) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                /*editUser =*/ apiService.editUser(user._id, user)


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    fun deleteUser(id: Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
               apiService.deleteUser(id)


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
