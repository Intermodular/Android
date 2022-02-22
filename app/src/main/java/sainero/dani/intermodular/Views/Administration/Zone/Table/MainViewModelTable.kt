package sainero.dani.intermodular.Views.Administration.Zone.Table

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceTable
import sainero.dani.intermodular.Api.ApiServiceZone
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Zonas

class MainViewModelTable: ViewModel() {

    //Consultas BD
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
                    Log.d("Error to get mesas","Error to get mesas")
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var table: Mesas = Mesas(0,"",0,"",0,)

    fun getMesaById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiServiceTable.getInstance()

            try {
                val result = apiService.getTableById(id)
                if (result.isSuccessful) {
                    table = result.body()!!
                }
                else
                    Log.d("Error to get mesa","Error to get mesa")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var zonesListResponse: List <Zonas> by mutableStateOf ( listOf ())
    fun getZoneList() {
        viewModelScope.launch {
            val apiService = ApiServiceZone.getInstance()

            try {
                val zoneList = apiService.getZones()
                zonesListResponse = zoneList

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

    //Métodos put
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

    //Validaciones
    fun isInteger(text: String): Boolean {
        try {
            return text.toInt() >= 0
        } catch (e: NumberFormatException) {
            return false
        }
    }

    fun checkAllValidations(
        textNºMesas: String,
        textNºChairs: String
    ): Boolean {
        if(
            !isInteger(textNºMesas) ||
            !isInteger(textNºChairs)
        ) return false
        return true
    }



}
