package sainero.dani.intermodular.Views.Administration.Products.Types.Extras

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import sainero.dani.intermodular.DataClass.Extras
import androidx.lifecycle.ViewModel
import sainero.dani.intermodular.DataClass.Tipos

class MainViewModelExtras : ViewModel() {

    var _extras: MutableList<Extras> = mutableListOf()
    var extrasState = "New"

    var _tmpExtras: MutableList<Extras> = mutableListOf()

    var tmpType: Tipos = Tipos(0,"","", arrayListOf())


    fun addTmpExtras(newValue: Extras) {
        _tmpExtras.add(newValue)
    }

    fun addExtras(newValue: Extras) {
        _extras.add(newValue)
    }

    fun getExtra() : MutableList<Extras> {
        return _extras
    }
}