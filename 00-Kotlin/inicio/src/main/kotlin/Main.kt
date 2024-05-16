import java.util.*
import java.util.function.IntUnaryOperator

fun main(){
    println("Hola mundo")

    //Inmutables (no se pueden reasignar)
    val inmutable: String = "Sebastian"
    //inmutable = "Adrian" //Error

    //Mutables
    var mutable: String = "Vicente"
    mutable = "Adrian"

    //Duck Typing
    var ejemploVariable = "Sebastian"
    var edadEjemplo = 32
    ejemploVariable.trim()

    //Variables primitivas
    val nombre: String = "Sebastian"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clases en Java
    val fechaNacimiento: Date = Date()

    //when (switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    //Llamar a la funciones
    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    //Named parameters
    //calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    //Uso de clases
    val sumaUno = Suma(1,1) //new suma(1,1) en KOTLIN no hay "new"
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos
    //Estaticos
    val arregloEstatico: Array<Int> = arrayOf(1,2,3)
    println(arregloEstatico)
    //Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOREACH => Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach{ valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }
    //"it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach{ println("Valor actual (it): ${it}") }

    // MAP -> modifica el arreglo
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve un nuevo arreglo con valores de las iteraciones
    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)
}

fun imprimirNombre(nombre: String): Unit{
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional(por defecto)
    bonoEspecial: Double ? = null //Opcional (nullable)
    //Variable? -> "?" Es nullable (en algun momento puede ser nula)
): Double{
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    //Date -> Date? (nullable)
    if (bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( //Constructor Primario
    // Caso 1) Parametro normal
    // uno:Int, (parametro (sin modificar acceso))

    // Caso 2) Parametro y propiedad (atributo) (private)
    // private var uno: Int (propiedad "instancia.uno")

    protected val numeroUno: Int, //instancia.numeroUno
    protected val numeroDos: Int, //instancia.numeroDos
){
    init { //bloque constructor primario (opcional)
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

class Suma( //Constructor primario
    unoParametro: Int, //Parametro
    dosParametro: Int, //Parametro
): Numeros( //Clase papa, Numeros(extendiendo)
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito : String = "Explicito" //Publico
    val soyPublicoImplicito : String = "Implicito" //Publicas (propiedades, metodos)
    init { //Bloque codigo constructor primario
        //this.unoParametro //ERROR no existe
        this.numeroUno
        this.numeroDos
        numeroUno //this. OPCIONAL (propiedades, metodos)
        numeroDos //this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito //this. OPCIONAL (propiedades, metodos)
    }

    constructor( //constructor secundario
        uno: Int?,
        dos: Int
    ):this(
        if(uno == null) 0 else uno,
        dos
    )

    constructor( //constructor terciario
        uno: Int,
        dos: Int?
    ):this(
        if(dos == null) 0 else dos,
        uno
    )

    constructor( //constructor cuaternario
        uno: Int?,
        dos: Int?
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )

    fun sumar() : Int{
        val total = numeroUno + numeroDos
        //Suma.agregarHistorial(total)  ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }

    companion object{ //Comparte entre todas las instancias, similar sl Static
        val pi = 3.14
        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma: Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}