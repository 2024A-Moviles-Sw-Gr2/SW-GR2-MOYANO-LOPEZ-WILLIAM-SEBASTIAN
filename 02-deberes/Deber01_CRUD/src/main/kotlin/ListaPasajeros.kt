import java.io.File

class ListaPasajeros (
    private val listaPasajeros: ArrayList<Pasajero> = ArrayList(),
){
    fun agregarPasajero(pasajero: Pasajero) {
        listaPasajeros.add(pasajero)
    }

    fun eliminarPasajero(pasajero: Pasajero) {
        listaPasajeros.remove(pasajero)
    }

    fun buscarPasajero(id: Int): Pasajero? {
        val avion= listaPasajeros.find { it.id == id }
        if(avion != null){
            return avion
        }
        return null
    }

    fun actualizarPasajero(
        pasajero: Pasajero,
        nombre: String,
        fechaNacimiento: String,
        mayorEdad: Boolean,
        numeroTelefono: Any,
        peso: Any
    ){
            pasajero.nombre = nombre
            pasajero.fechaNacimiento = fechaNacimiento
            pasajero.mayorEdad = mayorEdad
            pasajero.numeroTelefono = numeroTelefono
            pasajero.peso = peso
    }

    fun escribirArchivo(){
        File("src\\main\\kotlin\\archivos\\pasajero.txt").writeText(this.listaPasajeros.toString())
    }

    override fun toString(): String {
        var respuesta = ""
        this.listaPasajeros
            .forEach{pasajero: Pasajero ->
                respuesta += "${pasajero}\n"
            }
        return respuesta
    }
}