package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Zonas(
    val _id: Int,
    @SerializedName("Nombre") var name: String,
    @SerializedName("Nºmesas") var nºTables: Int
)
