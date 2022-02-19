package sainero.dani.intermodular.DataClass

data class LineaPedido(
    val producto: Productos,
    val cantidad: Int,
    var anotaciones: String,
    val lineasExtras: MutableList<LineaExtra>,
    val costeLinea: Float
)
