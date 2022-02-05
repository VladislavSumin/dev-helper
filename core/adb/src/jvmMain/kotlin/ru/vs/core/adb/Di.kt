package ru.vs.core.adb

import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.interactor.StopAdbInteractor
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.adb.domain.AdbServerInteractor
import ru.vs.core.adb.domain.AdbServerInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.coreAdb() = DI.Module("core-adb") {
    bindSingleton { StartAdbInteractor() }
    bindSingleton { StopAdbInteractor() }

    bindSingleton<AdbServerInteractor> { AdbServerInteractorImpl(i(), i()) }
}