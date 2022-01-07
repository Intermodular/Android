package sainero.dani.intermodular.DataClass

data class Productos(
    val _id: String,
    val nombre: String,
    val tipo: String,
    val ingredientes: MutableList<String>,
    val precio: Float,
    val especificaciones: MutableList<String>,
    val imagen: String,
    val stock: Int
)
