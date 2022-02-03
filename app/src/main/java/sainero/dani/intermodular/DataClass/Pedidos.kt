package sainero.dani.intermodular.DataClass

data class Pedidos(
    val _id: Int,
    val mesa: String,
    val productos: MutableList<String>,
    val fecha: String,
    val horaPago: String,
    val pagado: Boolean,
    val importeTotal: Float
)
