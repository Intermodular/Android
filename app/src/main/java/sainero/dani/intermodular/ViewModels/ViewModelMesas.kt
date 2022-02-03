package sainero.dani.intermodular.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiService
import sainero.dani.intermodular.DataClass.Mesas

class ViewModelMesas: ViewModel()  {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var mesasListResponse: List <Mesas> by mutableStateOf ( listOf ())
    fun getMesaList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val mesasList = apiService.getTables()
                mesasListResponse = mesasList

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var mesa: List <Mesas> by mutableStateOf(listOf())

    fun getMesaById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val mesaById = apiService.getTableById(id)
                mesa = mesaById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post


    //var newMesa: Mesas by mutableStateOf(Mesas(0,"error"))

    fun uploadMesa(mesa: Mesas) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.uploadTable(mesa)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun editMesa(mesa: Mesas) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.editTable()

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    fun deleteMesa(id: Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.deleteTable(id)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}