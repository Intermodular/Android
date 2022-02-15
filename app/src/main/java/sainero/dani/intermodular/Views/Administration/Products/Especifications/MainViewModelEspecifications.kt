package sainero.dani.intermodular.Views.Administration.Products.Especifications

import androidx.lifecycle.ViewModel
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.DataClass.Tipos

class MainViewModelEspecifications: ViewModel() {

    var _especifications: MutableList<String> = mutableListOf()
    var especificationsState = "New"

    var _tmpEspecifications: MutableList<String> = mutableListOf()

    fun addTmpEspecifications(newValue: String) {
        _tmpEspecifications.add(newValue)
    }

    fun addExtras(newValue: String) {
        _especifications.add(newValue)
    }

    fun getEspecifications() : MutableList<String> {
        return _especifications
    }
}