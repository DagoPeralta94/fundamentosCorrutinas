package com.example.fundamentoscorrutinas

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

fun main() {
    //dispatchers()
    //nested()
    //changeWithContext()
    //sequences()
    basicFlows()
}

fun basicFlows() {
    newTopic("Flows básicos")
    runBlocking {
        launch {
            getDataByFlow().collect() { println(it) }
        }

        launch {
            (1..50).forEach {
                delay(someTime()/10)
                println("Tarea 2...")
            }
        }
    }
}

fun getDataByFlow(): Flow<Float> {
    return flow {
        (1..5).forEach {
            println("procesando datos...")
            delay(someTime())
            emit(20 + it + Random.nextFloat())
        }

    }
    /*
fun sequences() {
    newTopic("Sequences")
    getDataBySeq().forEach { println("${it}°C") }
}
*/
    fun getDataBySeq(): Sequence<Float> {
        return sequence {
            (1..5).forEach {
                println("procesando datos...")
                Thread.sleep(someTime())
                yield(20 + it + Random.nextFloat())
            }
        }
    }

    fun changeWithContext() {
        runBlocking {
            newTopic("WithContext")
            startMsg()
            withContext(newSingleThreadContext("Cursos Android ANT")) {
                startMsg()
                delay(someTime())
                println("CursosAndroidANT")
                endMsg()
            }
            endMsg()
        }
    }

    fun nested() {
        runBlocking {
            newTopic("Anidar")
            val job = launch {
                startMsg()

                launch {
                    startMsg()
                    delay(someTime())
                    println("Otra tarea")
                    endMsg()
                }
                val subJob = launch(Dispatchers.IO) {
                    startMsg()
                    launch(newSingleThreadContext("Curso")) {
                        startMsg()
                        println("Tarea curso Android ant")
                        endMsg()
                    }
                    delay(someTime())
                    println("Tarea en el servidor  ")
                    endMsg()
                }
                delay(someTime() / 4)
                subJob.cancel()
                println("SubJob Cancelado...")

                var sum = 0
                (1..100).forEach {
                    sum += it
                    delay(someTime() / 100)
                }
                println("Sum = $sum")
                endMsg()
            }
            delay(someTime() / 2)
            job.cancel()
            println("Job Cancelado...")
        }
    }

    fun dispatchers() {
        runBlocking {
            newTopic("Dispatchers...")
            launch {
                startMsg()
                println("None")
                endMsg()
            }
            launch(Dispatchers.IO) {
                startMsg()
                println("IO")
                endMsg()
            }
            launch(Dispatchers.Unconfined) {
                startMsg()
                println("Unconfined")
                endMsg()
            }
            //Main solo para Android
            /*
        launch(Dispatchers.Main) {
            startMsg()
            println("Main")
            endMsg()
        }
         */
            launch(Dispatchers.Default) {
                startMsg()
                println("Default")
                endMsg()
            }
        }
    }
}
