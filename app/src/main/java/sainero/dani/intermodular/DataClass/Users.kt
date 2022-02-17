package sainero.dani.intermodular.DataClass

import com.google.gson.annotations.SerializedName
import java.net.Inet4Address

data class Users(
    val _id: Int,
    @SerializedName("nombre") val name: String,
    @SerializedName("apellido") val surname: String,
    val dni: String,
    @SerializedName("telefono") val phoneNumber: String,
    val fnac: String,
    @SerializedName("usuario") val user: String,
    val password: String,
    val rol: String,
    val email: String,
    @SerializedName("direccion") val address: String,
    val newUser: Boolean
)
