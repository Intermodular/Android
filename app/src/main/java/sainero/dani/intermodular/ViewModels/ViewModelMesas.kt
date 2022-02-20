package sainero.dani.intermodular.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceTable
import sainero.dani.intermodular.DataClass.Mesas

class ViewModelMesas: ViewModel()  {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var mesasListResponse: List <Mesas> by mutableStateOf ( listOf ())
    fun getMesaList() {
        viewModelScope.launch {
            val apiService = ApiServiceTable.getInstance()

            try {

                val result = apiService.getTables()
                if (result.isSuccessful) {
                    mesasListResponse = result.body()!!
                }
                else {
                    Log.d("Error to get tables","Error to get tables")
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var table: List <Mesas> by mutableStateOf(listOf())

    fun getMesaById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiServiceTable.getInstance()

            try {
                val mesaById = apiService.getTableById(id)
                table = mesaById
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var newMesa: MutableList<Mesas> = mutableListOf()
    fun uploadMesa(mesa: Mesas) {
        viewModelScope.launch {
            val apiService = ApiServiceTable.getInstance()

            try {
                val result = apiService.uploadTable(mesa)
                if (result.isSuccessful)
                    newMesa.add(result.body()!!)
                else
                    Log.d("Error: upload mesa","Error: upload mesa")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var editMesa: MutableList<Mesas> = mutableListOf()
    fun editMesa(mesa: Mesas) {
        viewModelScope.launch {
            val apiService = ApiServiceTable.getInstance()

            try {
                val result = apiService.editTable(mesa = mesa)
                if (result.isSuccessful)
                    newMesa.add(result.body()!!)
                else
                    Log.d("Error: edit table","Error: edit table")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    var deleteMesa: MutableList<Mesas> = mutableListOf()
    fun deleteMesa(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceTable.getInstance()

            try {

                val result =  apiService.deleteTable(id)
                if (result.isSuccessful)
                    deleteMesa.add(result.body()!!)
                else
                    Log.d("Error: delete table","Error: delete table")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}