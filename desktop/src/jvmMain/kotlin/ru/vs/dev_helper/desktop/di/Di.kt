package ru.vs.dev_helper.desktop.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import ru.vs.core.adb.coreAdb
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.dev_helper.desktop.ui.main.MainScreenView
import ru.vs.dev_helper.desktop.ui.main.MainScreenViewModel

fun createDi() = DI.lazy {
    importOnce(Modules.coreAdb())

    bindProvider { MainScreenViewModel(i(), i()) }
}