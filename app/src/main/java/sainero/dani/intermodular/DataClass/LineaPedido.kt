package sainero.dani.intermodular.DataClass

data class LineaPedido(
    val producto: Productos,
    val cantidad: Int,
    val anotaciones: String,
    val lineasExtra: MutableList<LineaExtra>,
    val costeLinea: Float
)
