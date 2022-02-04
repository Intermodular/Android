package sainero.dani.intermodular.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sainero.dani.intermodular.Api.ApiServiceExtra
import sainero.dani.intermodular.DataClass.Extras


class ViewModelExtras: ViewModel() {
    private var errorMessage: String by mutableStateOf ("")


    //Métodos get
    var extrasListResponse: List <Extras> by mutableStateOf ( listOf ())
    fun getExtrasList() {
        viewModelScope.launch {
            val apiService = ApiServiceExtra.getInstance()

            try {
                val extrasList = apiService.getExtras()
                extrasListResponse = extrasList

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var extra: List <Extras> by mutableStateOf(listOf())
    fun getExtrasById(id:Int) {
        viewModelScope.launch {
            val apiService = ApiServiceExtra.getInstance()

            try {
                val extraById = apiService.getExtraById(id)
                extra = extraById

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos post
    var newExtra: Extras by mutableStateOf(Extras(0,"error"))
    fun uploadExtra(extra: Extras) {
        viewModelScope.launch {
            val apiService = ApiServiceExtra.getInstance()

            try {
                val result = apiService.uploadExtra(extra)
                if (result.isSuccessful)
                    newExtra = result.body()!!
                else
                    Log.d("Error: upload extra","Error: upload extra")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    var editExtra: Extras by mutableStateOf(Extras(0,"error"))
    fun editExtra(extra: Extras) {
        viewModelScope.launch {
            val apiService = ApiServiceExtra.getInstance()

            try {
                val result = apiService.editExtra(extra = extra)
                if (result.isSuccessful)
                    editExtra = result.body()!!
                else
                    Log.d("Error: edit extra","Error: edit extra")


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //Métodos Delete
    fun deleteExtra(id: Int) {
        viewModelScope.launch {
            val apiService = ApiServiceExtra.getInstance()

            try {
                apiService.deleteExtra(id)


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}