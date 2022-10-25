package com.example.fundamentoscorrutinas

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

fun main(){
    //coldFlow()
    //cancelFlow()
    flowOperators()

}

fun flowOperators() {
    runBlocking {
        newTopic("Operadores Intermediarios") //Transformar un flujo por medio de los mismos, no se consumen recursos hasta que sean solicitados
        newTopic("Map")
        getDataByFlow()
            .map {
                setFormat(it)
                setFormat(converCelsToFahr(it), "F")
            }
            //.collect { println(it) }
        newTopic("Filter")
        getDataByFlow()
            .filter {
                it < 23
            }
            .map {
                setFormat(it)
            }
            .collect { println(it) }
    }
}
fun converCelsToFahr(cels: Float): Float = ((cels*9)/5)+32
fun setFormat(temp: Float, degree: String = "C"): String = String.format(Locale.getDefault(),
    "%.1f°$degree", temp)

fun cancelFlow() {
    runBlocking {
        newTopic("Cancelar flow")
        val job = launch {
            getDataByFlow().collect { println(it) }
        }
        delay(someTime()*2)
        job.cancel()
    }
}

fun coldFlow() {
    newTopic("Flows are Cold")
    runBlocking {
        val dataFlow = getDataByFlow()
        println("Esperando...")
        delay(someTime())
        dataFlow.collect{ println(it) }
    }
}

