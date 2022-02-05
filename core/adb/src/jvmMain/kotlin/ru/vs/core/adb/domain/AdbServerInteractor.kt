package ru.vs.core.adb.domain

import co.touchlab.kermit.Logger
import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.interactor.StopAdbInteractor

interface AdbServerInteractor {
    suspend fun startServer()
    suspend fun stopServer()
}

internal class AdbServerInteractorImpl(
    private val startAdbInteractor: StartAdbInteractor,
    private val stopAdbInteractor: StopAdbInteractor,
) : AdbServerInteractor {
    override suspend fun startServer() {
        val result = startAdbInteractor.execute()
        if (result) {
            Logger.i("Adb server started")
        } else {
            throw RuntimeException("Failed to start adb server")
        }
    }

    override suspend fun stopServer() {
        val result = stopAdbInteractor.execute()
        if (result) {
            Logger.i("Adb server stopped")
        } else {
            throw RuntimeException("Failed to stop adb server")
        }
    }
}