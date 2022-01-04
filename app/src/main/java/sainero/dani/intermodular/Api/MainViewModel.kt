package sainero.dani.intermodular.Api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.*
import sainero.dani.intermodular.Api.*

class MainViewModel : ViewModel() {


    private val _usersList = mutableStateListOf<Users>()
    var errorMessage: String by mutableStateOf ("")
    val usersList: List<Users>
        get() = _usersList

    fun getUserList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                _usersList.clear()
                _usersList.addAll(apiService.getUsers())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }


   /* var userListResponse: List <Users> by mutableStateOf ( listOf ())
    var errorMessage: String by mutableStateOf ("")

    fun getUserList () {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val usersList = apiService.getUsers()
                userListResponse = usersList
            }
            catch (e: Exception) {
                errorMessage = e.message. toString ()
            }
        }
    }*/
}