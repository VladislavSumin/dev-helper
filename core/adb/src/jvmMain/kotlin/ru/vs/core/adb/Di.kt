package ru.vs.core.adb

import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.interactor.StopAdbInteractor
import com.malinskiy.adam.transport.vertx.VertxSocketFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.adb.domain.AdbClientInteractor
import ru.vs.core.adb.domain.AdbClientInteractorImpl
import ru.vs.core.adb.domain.AdbDeviceInteractor
import ru.vs.core.adb.domain.AdbDeviceInteractorImpl
import ru.vs.core.adb.domain.AdbDevicesInteractor
import ru.vs.core.adb.domain.AdbDevicesInteractorImpl
import ru.vs.core.adb.domain.AdbServerInteractor
import ru.vs.core.adb.domain.AdbServerInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.coreAdb() = DI.Module("core-adb") {
    bindSingleton<CoroutineScope>(Di.Scope.Application) { GlobalScope }

    bindSingleton { VertxSocketFactory() }
    bindSingleton { StartAdbInteractor() }
    bindSingleton { StopAdbInteractor() }
    bindSingleton { AndroidDebugBridgeClientFactory().apply { socketFactory = i() }.build() }

    bindSingleton<AdbClientInteractor> { AdbClientInteractorImpl(i()) }
    bindSingleton<AdbDeviceInteractor> { AdbDeviceInteractorImpl(i(), i(Di.Scope.Application)) }
    bindSingleton<AdbDevicesInteractor> { AdbDevicesInteractorImpl(i(), i(Di.Scope.Application)) }
    bindSingleton<AdbServerInteractor> { AdbServerInteractorImpl(i(), i()) }
}

object Di {
    enum class Scope {
        Application
    }
}