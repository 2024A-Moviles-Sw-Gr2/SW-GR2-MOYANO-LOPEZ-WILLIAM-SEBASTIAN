import java.util.concurrent.atomic.AtomicInteger

class Avion(
    var nombre: String,
    var fechaConstruccion: String,
    var cantidadPasajeros: Any,
    var pesoMaximo: Any,
    var disponible: Boolean,
    var listaPasajeros: ListaPasajeros? = null,
) {
    val id: Int = generarId()

    companion object {
        private val contador: AtomicInteger = AtomicInteger(0)
        private fun generarId(): Int = contador.incrementAndGet()
    }

    fun agregarListaPasajeros(listaPasajeros: ListaPasajeros) {
        this.listaPasajeros = listaPasajeros
    }

    override fun toString(): String {
        return "Nombre='$nombre'\n, fechaConstruccion=$fechaConstruccion\n, cantidadPasajeros=$cantidadPasajeros\n, pesoMaximo=$pesoMaximo\n, disponible=$disponible\n, listaPasajeros=$listaPasajeros\n, id=$id\n)"
    }
}