package sainero.dani.intermodular.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceUser
import sainero.dani.intermodular.DataClass.Users

class ViewModelUsers: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


//Métodos get
    var userListResponse: List <Users> by mutableStateOf ( listOf ())
    fun getUserList() {
        viewModelScope.launch {
            val apiService = ApiServiceUser.getInstance()

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
            val apiService = ApiServiceUser.getInstance()

            try {
                val userById = apiService.getUserById(id)
                user = userById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var newUser: MutableList<Users> = mutableListOf()
    fun uploadUser(user: Users) {
        viewModelScope.launch {
            val apiService = ApiServiceUser.getInstance()

            try {
                val response = apiService.uploadUser(user)
                if (response.isSuccessful)
                    newUser.add(response.body()!!)
                else
                    Log.d("Error: upload user","Error: upload user")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var editUser: MutableList<Users> = mutableListOf()
    fun editUser(user: Users) {
        viewModelScope.launch {
            val apiService = ApiServiceUser.getInstance()

            try {
                val response = apiService.editUser(user)
                if (response.isSuccessful)
                    editUser.add(response.body()!!)
                else
                    Log.d("Error: edit user","Error: edit user")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    var deleteUser: MutableList<Users> = mutableListOf()
    fun deleteUser(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceUser.getInstance()

            try {
                val response = apiService.deleteUser(id)
                if (response.isSuccessful)
                    deleteUser.add(response.body()!!)
                else
                    Log.d("Error: delete user","Error: delete user")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
