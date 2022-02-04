package sainero.dani.intermodular.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceZone
import sainero.dani.intermodular.DataClass.Zonas

class ViewModelZonas: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
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

    var zone: List <Zonas> by mutableStateOf(listOf())
    fun getZoneById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiServiceZone.getInstance()

            try {
                val zoneById = apiService.getZoneById(id)
                zone = zoneById
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var uploadZone: MutableList<Zonas> = mutableListOf()
    fun uploadZone(zone: Zonas) {
        viewModelScope.launch {
            val apiService = ApiServiceZone.getInstance()

            try {
                val response = apiService.uploadZone(zone)
                if (response.isSuccessful)
                    uploadZone.add(response.body()!!)
                else
                    Log.d("Error: upload zone","Error: uplaod zone")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var editZone: MutableList<Zonas> = mutableListOf()
    fun editZone(zone: Zonas) {
        viewModelScope.launch {
            val apiService = ApiServiceZone.getInstance()

            try {
                val response = apiService.editZone(zone)
                if (response.isSuccessful)
                    editZone.add(response.body()!!)
                else
                    Log.d("Error: edit zone","Error: edit zone")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    var deleteZone: MutableList<Zonas> = mutableListOf()
    fun deleteZone(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceZone.getInstance()

            try {
                val response = apiService.deleteZone(id)
                if (response.isSuccessful)
                    deleteZone.add(response.body()!!)
                else
                    Log.d("Error: delete zone","Error: delete zone")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}