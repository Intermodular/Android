package sainero.dani.intermodular.Views.Administration.Employee

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

class MainViewModelEmployee: ViewModel() {

    //Consultas BD
    private var errorMessage: String by mutableStateOf ("")

    //Métodos get
    var userListResponse: List <Users> by mutableStateOf ( listOf ())
    fun getUserList() {
        viewModelScope.launch {
            val apiService = ApiServiceUser.getInstance()

            try {
                val result = apiService.getUsers()
                if(result.isSuccessful) {
                    userListResponse = result.body()!!
                }
                else {
                    Log.d("Error","Error")
                }

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

    //Métodos put
    var editUser: MutableList<Users> = mutableListOf()
    fun editUser(user: Users) {
        viewModelScope.launch {
            val apiService = ApiServiceUser.getInstance()

            try {
                val response = apiService.editUser(user)
                apiService.getUsers()
                if (response.isSuccessful) {
                    editUser.add(response.body()!!)
                }
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

    //Validaciones
    fun isValidDni(text: String) = Pattern.compile("^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidName(text: String) = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidSurname(text: String) = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidPhoneNumber(text: String) = Pattern.compile("^(([+][0-9]{2}?)?[0-9]{9})?$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidDateOfBirth(text: String) = Pattern.compile("^(([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2}))?\$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidUser(text: String) = Pattern.compile("^[a-zA-Z0-9]+\$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$", Pattern.CASE_INSENSITIVE).matcher(text).find()

    fun checkAllValidations(
        textDni: String,
        textName: String,
        textSurname:String,
        textPhoneNumber: String,
        textDateOfBirth: String,
        textUser: String,
        textEmail: String
    ): Boolean {
        if (
            !isValidDni(text = textDni) ||
            !isValidName(text = textName) ||
            !isValidSurname(text = textSurname) ||
            !isValidPhoneNumber(text = textPhoneNumber) ||
            !isValidDateOfBirth(text = textDateOfBirth) ||
            !isValidUser(text = textUser) ||
            !isValidEmail(text = textEmail)
        )  return false

        return  true
    }
}


