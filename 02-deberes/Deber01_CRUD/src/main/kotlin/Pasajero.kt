import java.util.concurrent.atomic.AtomicInteger

class Pasajero(
    var nombre: String,
    var fechaNacimiento: String,
    var mayorEdad: Boolean,
    var numeroTelefono: Any,
    var peso: Any
) {
    val id: Int = generarId()

    companion object {
        private val contador: AtomicInteger = AtomicInteger(0)
        private fun generarId(): Int = contador.incrementAndGet()
    }

    override fun toString(): String {
        return "Nombre='$nombre\n', fechaNacimiento=$fechaNacimiento\n, mayorEdad=$mayorEdad\n, numeroTelefono=$numeroTelefono\n, peso=$peso\n, id=$id\n)"
    }


}