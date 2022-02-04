package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Extras(
    val _id : Int,
    @SerializedName("nombre") val name: String,
)
