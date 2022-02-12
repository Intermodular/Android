package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Extras(
    @SerializedName("nombre") var name: String,
    @SerializedName("precio") var price: Float,
)
