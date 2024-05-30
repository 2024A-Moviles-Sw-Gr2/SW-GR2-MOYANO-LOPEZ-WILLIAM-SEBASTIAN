import java.text.ParseException
import java.text.SimpleDateFormat

fun main() {

    val listaPasajeros = ListaPasajeros()
    val listaAviones = ListaAviones()

    while (true) {
        println("Selecciona una opción:")
        println("1. Aviones")
        println("2. Pasajeros")
        println("3. Salir")

        val input = readlnOrNull()

        when (input) {
            "1" -> {
                println("Has seleccionado la Opción 1")
                // Lógica para la Opción 1

                while(true){
                    println("Selecciona una opcion")
                    println("1. Ver aviones")
                    println("2. Agregar avion")
                    println("3. Eliminar avion")
                    println("4. Actualizar avion")
                    println("5. Salir")

                    val opcion = readlnOrNull()

                    when (opcion) {
                        "1" -> {
                            println(listaAviones.toString())
                        }
                        "2" -> {
                            val avion = insertarDatosAvion()
                            listaAviones.agregarAvion(avion)
                            listaAviones.escribirArchivo()
                        }

                        "3" -> {
                            println("Escriba el id del avion a eliminar")
                            val id: Int = readlnOrNull()?.toIntOrNull() ?: run {
                                println("Entrada no válida.")
                                return
                            }

                            val avion = listaAviones.buscarAvion(id)
                            if(avion != null) {
                                listaAviones.eliminarAvion(avion)
                                listaAviones.escribirArchivo()
                            }
                        }

                        "4" -> {
                            println("Escriba el id del avion que quiere actualizar")
                            val id: Int = readlnOrNull()?.toIntOrNull() ?: run {
                                println("Entrada no válida.")
                                return
                            }

                            val avion = listaAviones.buscarAvion(id)
                            if(avion != null) {
                                val avionInsertar = insertarDatosAvion()
                                val nombre = avionInsertar.nombre
                                val fechaConstruccion = avionInsertar.fechaConstruccion
                                val cantidadPasajeros = avionInsertar.cantidadPasajeros
                                val pesoMaximo = avionInsertar.pesoMaximo
                                val disponible = avionInsertar.disponible
                                listaAviones.actualizarAvion(avion, nombre, fechaConstruccion, cantidadPasajeros, pesoMaximo, disponible)
                                listaAviones.escribirArchivo()
                            }
                        }
                        "5" -> {
                            println("Saliendo al menu principal")
                            break
                        }
                        else -> {
                            println("Opción no válida, por favor intenta de nuevo.")
                        }
                    }
                }

            }
            "2" -> {
                println("Has seleccionado la Opción 2")

                while(true){
                    println("Selecciona una opcion")
                    println("1. Ver pasajeros")
                    println("2. Agregar pasajero")
                    println("3. Eliminar pasajero")
                    println("4. Actualizar pasajero")
                    println("5. Agregar pasajeros a avion")
                    println("6. Salir")

                    val opcion = readlnOrNull()

                    when (opcion) {
                        "1" -> {
                            println(listaPasajeros.toString())
                        }
                        "2" -> {
                            val pasajero = insertarDatosPasajero()
                            listaPasajeros.agregarPasajero(pasajero)
                            listaPasajeros.escribirArchivo()
                        }

                        "3" -> {
                            println("Escriba el id del pasajero a eliminar")
                            val id: Int = readlnOrNull()?.toIntOrNull() ?: run {
                                println("Entrada no válida.")
                                return
                            }

                            val pasajero = listaPasajeros.buscarPasajero(id)
                            if(pasajero != null) {
                                listaPasajeros.eliminarPasajero(pasajero)
                                listaPasajeros.escribirArchivo()
                            }
                        }

                        "4" -> {
                            println("Escriba el id del pasajero que quiere actualizar")
                            val id: Int = readlnOrNull()?.toIntOrNull() ?: run {
                                println("Entrada no válida.")
                                return
                            }

                            val pasajero = listaPasajeros.buscarPasajero(id)
                            if(pasajero != null) {
                                val pasajeroInsertar = insertarDatosPasajero()
                                val nombre = pasajeroInsertar.nombre
                                val fechaNacimiento = pasajeroInsertar.fechaNacimiento
                                val mayorEdad = pasajeroInsertar.mayorEdad
                                val numeroTelefono  = pasajeroInsertar.numeroTelefono
                                val peso = pasajeroInsertar.peso
                                listaPasajeros.actualizarPasajero(pasajero, nombre, fechaNacimiento, mayorEdad, numeroTelefono, peso)
                                listaPasajeros.escribirArchivo()
                            }
                        }
                        "5" -> {
                            println(listaAviones.toString())
                            println("Escriba el id del avion")
                            val id: Int = readlnOrNull()?.toIntOrNull() ?: run {
                                println("Entrada no válida.")
                                return
                            }
                            val avion = listaAviones.buscarAvion(id)
                            if(avion != null) {
                                avion.agregarListaPasajeros(listaPasajeros)
                                listaAviones.escribirArchivo()
                            }else{
                                println("Avion no encontrado")
                            }
                        }
                        "6" -> {
                            println("Saliendo al menu principal")
                            break
                        }
                        else -> {
                            println("Opción no válida, por favor intenta de nuevo.")
                        }
                    }
                }
            }
            "3" -> {
                println("Saliendo del programa...")
                break
            }
            else -> {
                println("Opción no válida, por favor intenta de nuevo.")
            }
        }
    }
}

fun insertarDatosPasajero(): Pasajero {
    println("Nombre: ")
    val nombre: String = readlnOrNull().toString()

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    dateFormat.isLenient = false

    var fechaNacimiento: String
    while (true) {
        println("Fecha nacimiento: formato dd/MM/yyyy:")
        fechaNacimiento = readlnOrNull().toString()

        if (fechaNacimiento != null) {
            try {
                val date = dateFormat.parse(fechaNacimiento)
                println("Fecha válida: $date")
                break // Salir del bucle si la fecha es válida
            } catch (e: ParseException) {
                println("Formato de fecha no válido, por favor intenta de nuevo.")
            }
        } else {
            println("Entrada no válida, por favor intenta de nuevo.")
        }
    }

    println("Mayor de edad? S/N")
    val mayorEdad: Boolean
    val respuesta = readlnOrNull()
    if(respuesta == "S") {
        mayorEdad = true
    }else{
        mayorEdad = false
    }

    println("Numero de telefono:")
    val numeroTelefono = readlnOrNull()?.toIntOrNull() ?: run {
        println("Entrada no válida para cantidad de pasajeros.")
    }

    println("Peso: ")
    val peso = readlnOrNull()?.toDoubleOrNull() ?: run {
        println("Entrada no válida para peso máximo.")
    }

    val pasajero = Pasajero(
        nombre = nombre,
        fechaNacimiento = fechaNacimiento,
        numeroTelefono = numeroTelefono,
        peso = peso,
        mayorEdad = mayorEdad
    )
    return pasajero
}

fun insertarDatosAvion(): Avion{
    println("Nombre: ")
    val nombre: String = readlnOrNull().toString()

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    dateFormat.isLenient = false

    var fechaConstruccion: String
    while (true) {
        println("Fecha de construccion: formato dd/MM/yyyy:")
        fechaConstruccion = readlnOrNull().toString()

        if (fechaConstruccion != null) {
            try {
                val date = dateFormat.parse(fechaConstruccion)
                println("Fecha válida: $date")
                break // Salir del bucle si la fecha es válida
            } catch (e: ParseException) {
                println("Formato de fecha no válido, por favor intenta de nuevo.")
            }
        } else {
            println("Entrada no válida, por favor intenta de nuevo.")
        }
    }

    println("Cantidad de pasajeros:")
    val cantidadPasajeros = readlnOrNull()?.toIntOrNull() ?: run {
        println("Entrada no válida para cantidad de pasajeros.")
    }

    println("Peso máximo: ")
    val pesoMaximo = readlnOrNull()?.toDoubleOrNull() ?: run {
        println("Entrada no válida para peso máximo.")
    }

    println("Avion disponible? S/N")
    val disponible: Boolean
    val respuesta = readlnOrNull()
    if(respuesta == "S") {
        disponible = true
    }else{
        disponible = false
    }

    val avion = Avion(
        nombre = nombre,
        fechaConstruccion = fechaConstruccion,
        cantidadPasajeros = cantidadPasajeros,
        pesoMaximo = pesoMaximo,
        disponible = disponible
    )
    return avion
}
