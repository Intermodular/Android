package sainero.dani.intermodular.DataClass

data class Pedidos(
    val _id: Int,
    val idMesa: Int,
    val lineasPedido: MutableList<LineaPedido>
)
