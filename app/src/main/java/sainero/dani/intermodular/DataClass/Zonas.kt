package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Zonas(
    val _id: Int,
    @SerializedName("nombre") var name: String,
    @SerializedName("numMesas") var nºTables: Int
)
