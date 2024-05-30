import java.io.File
import kotlin.collections.ArrayList

class ListaAviones (
    private var listaAviones: ArrayList<Avion> = ArrayList()
){
    fun agregarAvion(avion: Avion){
        listaAviones.add(avion)
    }

    fun eliminarAvion(avion: Avion){
        this.listaAviones.remove(avion)
    }

    fun buscarAvion(id: Int): Avion? {
        val avion= listaAviones.find { it.id == id }
        if(avion != null){
            return avion
        }
        return null
    }

    fun actualizarAvion(
        avion: Avion,
        nombre: String,
        fechaConstruccion: String,
        cantidadPasajeros: Any,
        pesoMaximo: Any,
        disponible: Boolean
    ) {
        avion.nombre = nombre
        avion.fechaConstruccion = fechaConstruccion
        avion.cantidadPasajeros = cantidadPasajeros
        avion.pesoMaximo = pesoMaximo
        avion.disponible = disponible
    }

    fun escribirArchivo(){
        File("src\\main\\kotlin\\archivos\\avion.txt").writeText(this.listaAviones.toString())
    }

    override fun toString(): String {
        var respuesta = ""
        this.listaAviones
            .forEach{avion: Avion ->
                respuesta += "\n${avion}\n"
            }
        return respuesta
    }
}