package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Mesas(
    val _id: Int,
    @SerializedName("zona") val zone: String,
    @SerializedName("numSillas") val numChair: Int,
    @SerializedName("estado") val state: String,
    @SerializedName("numero") val number: Int
)
