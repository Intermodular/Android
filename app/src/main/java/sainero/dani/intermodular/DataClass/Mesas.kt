package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Mesas(
    val _id: Int,
    @SerializedName("zona") var zone: String,
    @SerializedName("numSillas") var numChair: Int,
    @SerializedName("estado") var state: String,
    @SerializedName("numero") var number: Int
)
