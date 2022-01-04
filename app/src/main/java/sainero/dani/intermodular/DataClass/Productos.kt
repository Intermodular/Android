package sainero.dani.intermodular.DataClass

data class Productos(
    val nombre: String,
    val tipo: String,
    val ingredientes: MutableList<String>,
    val precio: Float,
    val especificaciones: MutableList<String>,
    val imagan: String,
    val stock: Int
)
