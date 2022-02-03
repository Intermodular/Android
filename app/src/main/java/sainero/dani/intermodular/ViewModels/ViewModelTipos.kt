package sainero.dani.intermodular.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiService
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Tipos

class ViewModelTipos: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var typeListResponse: List <Tipos> by mutableStateOf ( listOf ())
    fun getTypesList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val typeList = apiService.getTypes()
                typeListResponse = typeList

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var type: List <Tipos> by mutableStateOf(listOf())

    fun getTypeById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val tipoById = apiService.getTypeById(id)
                type = tipoById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post


    //var newType: Tipos by mutableStateOf()

    fun uploadType(tipo: Tipos) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.uploadType(tipo)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun editType(tipo: Tipos) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.editType()

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    fun deleteType(id: Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.deleteType(id)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}