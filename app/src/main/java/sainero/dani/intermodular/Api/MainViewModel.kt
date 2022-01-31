package sainero.dani.intermodular.Api

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.DataClass.*
import sainero.dani.intermodular.Api.*
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.test
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.userListResponse
import sainero.dani.intermodular.Views.Administration.Employee.ToastDemo

class MainViewModel : ViewModel() {



    var userListResponse: List <Users> by mutableStateOf ( listOf ())
    private var errorMessage: String by mutableStateOf ("")

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
}