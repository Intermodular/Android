package sainero.dani.intermodular.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiService
import sainero.dani.intermodular.DataClass.Zonas

class ViewModelZonas: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var zonesListResponse: List <Zonas> by mutableStateOf ( listOf ())
    fun getZoneList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

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
            val apiService = ApiService.getInstance()

            try {
                val zoneById = apiService.getZoneById(id)
                zone = zoneById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    fun uploadZone(zone: Zonas) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.uploadZone(zone)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun editZone(zone: Zonas) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                apiService.editZone(zone._id)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    fun deleteZone(id: Int) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                apiService.deleteZone(id)
                apiService.deleteZone(id)


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}