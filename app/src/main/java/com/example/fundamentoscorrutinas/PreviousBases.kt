package com.example.fundamentoscorrutinas

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    //lambda()
    //threads()
    coroutinesVsThreads()
}

fun coroutinesVsThreads() {
    newTopic("Corrutinas vs Threads")
    runBlocking {
        (1..1_000_000).forEach {
            launch {
                delay(someTime())
                print("*")
            }
        }
    }
}

private const val SEPARATOR = "===================="
fun newTopic(topic: String) {
    println("\n$SEPARATOR $topic $SEPARATOR\n")
}

fun lambda() {
    newTopic("Lambda")
    println(multi(2, 3))

    /*
    multilambda(2, 3, { result ->
        println(result)
    })
    */

    multilambda(2, 3) { result ->
        println(result)
    }
}

fun multi(x: Int, y: Int): Int {
    return x * y
}

fun multilambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    callback(x * y)
}

fun threads() {
    newTopic("Threads")
    println(multiThread(2, 3))
    multiThreadLambda(2, 3) {
        println("Thread+Lambda $it")
    }
}

fun multiThread(x: Int, y: Int): Int {
    var result = 0

    thread {
        Thread.sleep(someTime())
        result = x * y
    }
    Thread.sleep(2100)
    return result
}

fun multiThreadLambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    var result = 0

    thread {
        Thread.sleep(someTime())
        result = x * y
        callback(result)
    }
}

fun someTime(): Long = Random.nextLong(500, 2000)
