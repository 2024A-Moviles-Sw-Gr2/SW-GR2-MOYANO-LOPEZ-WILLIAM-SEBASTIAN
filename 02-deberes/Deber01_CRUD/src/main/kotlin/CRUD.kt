import java.io.File

open class CRUD(
    private val ubicacion: String
) {
    fun leer() : Unit{
        File(this.ubicacion).forEachLine { line ->
            println(line)
        }
    }

    fun crear(registro: String): Unit{
        File(this.ubicacion).appendText("\n$registro")
    }

    fun actualizar(lineaAActualizar: Int, nuevoRegistro: String) {
        val archivo = File(this.ubicacion)
        val lineas = archivo.readLines().toMutableList()

        if (lineaAActualizar < 0 || lineaAActualizar >= lineas.size) {
            println("Número de línea fuera de rango")
            return
        }

        lineas[lineaAActualizar] = nuevoRegistro
        archivo.writeText(lineas.joinToString("\n"))
    }

    fun eliminar(lineaAEliminar: Int) {
        val archivo = File(this.ubicacion)
        val lineas = archivo.readLines().toMutableList()

        if (lineaAEliminar < 0 || lineaAEliminar >= lineas.size) {
            println("Número de línea fuera de rango")
            return
        }

        lineas.removeAt(lineaAEliminar)
        archivo.writeText(lineas.joinToString("\n"))
    }
}