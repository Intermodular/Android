package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Tipos(
    val _id: Int,
    @SerializedName("nombre") val name: String,
    val img : String,
    @SerializedName("listaExtras") val compatibleExtras: MutableList<Any>

)
