package sainero.dani.intermodular.DataClass

data class Ticket(
    val _id : Int,
    val numMesa: Int,
    val lineasPedido: MutableList<LineaPedido>,
    val costeTotal: Float,
    val fechaYHoraDePago: String,
    val empleado: Users,
    val metodoPago: String
)
