package ru.vs.dev_helper.desktop

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() {
    runBlocking {
        yield()
        println("Hello")
    }
}