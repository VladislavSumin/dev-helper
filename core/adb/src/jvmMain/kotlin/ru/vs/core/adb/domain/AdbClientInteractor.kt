package ru.vs.core.adb.domain

import com.malinskiy.adam.transport.vertx.VertxSocketFactory

interface AdbClientInteractor {
    suspend fun closeAll()
}

internal class AdbClientInteractorImpl(
    private val socketFactory: VertxSocketFactory
) : AdbClientInteractor {
    override suspend fun closeAll() {
        socketFactory.close()
    }
}