package com.example.fundamentoscorrutinas

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

public var prueba: String = "prueba"

fun main() {
    //globalScope()
    suspendFun()
    //0cRunBlocking()
    //cLaunch()
    //cAsync()
    //job()
    //deferred()
    //cProduce()
    //cProduceInt()
//println("Algo1")
    //readLine()
    //println(prueba)
    //Thread.sleep(1000)
    //println(prueba)
}

fun cProduce() = runBlocking {
    newTopic("Produce")
    val names = produceNames()
    names.consumeEach { println(it) }
}
fun CoroutineScope.produceNames(): ReceiveChannel<String> = produce {
    (1..5).forEach { send("name$it") }
}
fun cProduceInt() = runBlocking {
    newTopic("Produce Int")
    val enteros = produceInts()
    enteros.consumeEach { println(it) }
}
fun CoroutineScope.produceInts(): ReceiveChannel<Int> = produce {
    (1..5).forEach { send(2 * it)}
}

fun cAsync() {
    runBlocking {
        newTopic("Async")
        val result = async {
            startMsg()
            delay(someTime())
            println("async...")
            endMsg()
        }
        println("Result ${result.await()}")
    }
}
fun job() {
    runBlocking {
        newTopic("Job")
        val job = launch {
            startMsg()
            delay(2_100)
            println("job...")
            endMsg()
        }
        println("Job: $job")

        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")

        delay(someTime())
        println("Tarea cancelada o interrumpida")
        job.cancel()

        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")
    }
}
fun deferred() {
    runBlocking {
        newTopic("Deferred")
        val deferred = async {
            startMsg()
            delay(someTime())
            println("deferred...")
            endMsg()
            multi(5, 2)
        }
        println("Deferred: $deferred")
        println("Valor del Deferred.await: ${deferred.await()}")

        val result = async {
            multi(3,3)
        }.await()
        println("Valor del result.await: $result")
    }
}

fun cLaunch() {
    runBlocking {
        launch {
            startMsg()
            delay(someTime())
            println("Launch")
            endMsg()
        }
    }
}

fun suspendFun() {
    newTopic("Suspend")
    //Thread.sleep(5000)
    //delay(someTime())
    GlobalScope.launch {
        Thread.sleep(2000)
        //delay(5000)
        //println("Algo2")
        //readLine()
        //println("Algo3")
        prueba = "Hola"
    }
    //Thread.sleep(10000)
    println("Algo4")
}

fun cRunBlocking(){
    newTopic("RunBlocking")
    runBlocking {
        startMsg()
        delay(someTime())
        println("runBlocking")
        readLine()
        endMsg()
    }
}

fun globalScope() {
    newTopic("Global Scope")
    GlobalScope.launch {
        startMsg()
        delay(someTime())
        println("Mi corrutina")
        endMsg()
    }
}

fun startMsg() {
    println("Comenzando corrutina -${Thread.currentThread().name}-")
}
fun endMsg() {
    println("Corrutina -${Thread.currentThread().name}- finalizada")
}
