package sainero.dani.intermodular.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceType
import sainero.dani.intermodular.DataClass.Tipos

class ViewModelTipos: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var typeListResponse: List <Tipos> by mutableStateOf ( listOf ())
    fun getTypesList() {
        viewModelScope.launch {
            val apiService = ApiServiceType.getInstance()

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
            val apiService = ApiServiceType.getInstance()

            try {
                val tipoById = apiService.getTypeById(id)
                type = tipoById
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var newType: MutableList<Tipos> = mutableListOf()
    fun uploadType(tipo: Tipos) {
        viewModelScope.launch {
            val apiService = ApiServiceType.getInstance()

            try {
                val result = apiService.uploadType(tipo)
                if (result.isSuccessful)
                    newType.add(result.body()!!)
                else
                    Log.d("Error: upload type","Error: upload type")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var editType: MutableList<Tipos> = mutableListOf()
    fun editType(tipo: Tipos) {
        viewModelScope.launch {
            val apiService = ApiServiceType.getInstance()

            try {
                val result = apiService.editType(tipo = tipo)
                if (result.isSuccessful)
                    newType.add(result.body()!!)
                else
                    Log.d("Error: edit type","Error: edit type")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }


    //Métodos Delete
    var deleteType: MutableList<Tipos> = mutableListOf()
    fun deleteType(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceType.getInstance()

            try {
                val result = apiService.deleteType(id)
                if (result.isSuccessful)
                    newType.add(result.body()!!)
                else
                    Log.d("Error: delete type","Error: delete type")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}