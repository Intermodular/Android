package sainero.dani.intermodular.DataClass

data class Tipos(
    val _id: Int,
    val nombre: String,
    val img : String,
    val extrasCompatibles: MutableList<Extras>
)
