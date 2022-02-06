package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Extras(
    @SerializedName("nombre") val name: String,
    @SerializedName("precio") val price: Float,
)
