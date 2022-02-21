package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Reservation(
    val _id: Int,
    @SerializedName("nombre") val name: String,
    @SerializedName("idMesa") val idTable: Int,
    @SerializedName("numMesa") val numTable: Int,
    @SerializedName("numComensales") val numDiners: Int,
    @SerializedName("year") val anyo: Int,
    @SerializedName("mes") val month: Int,
    @SerializedName("dia") val day: Int,
    @SerializedName("hora") val hour: Int,
    @SerializedName("minuto") val minute: Int
)
