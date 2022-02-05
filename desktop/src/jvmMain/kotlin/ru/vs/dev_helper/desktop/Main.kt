package ru.vs.dev_helper.desktop

import co.touchlab.kermit.Logger
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.core.adb.domain.AdbServerInteractor
import ru.vs.core.logging.setupDefault
import ru.vs.dev_helper.desktop.di.createDi

fun main() {
    Logger.setupDefault()
    val logger = Logger.withTag("main")
    logger.i("Starting dev-helper")

    val di = createDi()
    logger.i("DI graph created")


    val adbServerInteractor: AdbServerInteractor by di.instance()

    runBlocking {
        adbServerInteractor.startServer()
    }
}