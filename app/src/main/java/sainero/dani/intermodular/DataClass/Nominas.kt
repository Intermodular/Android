package sainero.dani.intermodular.DataClass

data class Nominas (
    val _id: Int,
    val empleado: String,
    val horas: Int,
    val euros: Int,
    val fechaInicio: String,
    val fechaFinal: String
)