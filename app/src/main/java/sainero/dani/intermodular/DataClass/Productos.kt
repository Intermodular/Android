package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName

data class Productos(
    val _id: Int,
    @SerializedName("nombre") val name: String,
    @SerializedName("tipo") val type: String,
    @SerializedName("ingredientes") var ingredients: MutableList<String>,
    @SerializedName("precio") val price: Float,
    @SerializedName("especificaciones") var especifications: MutableList<String>,
    @SerializedName("imagen") val img: String,
    val stock: Int
)
