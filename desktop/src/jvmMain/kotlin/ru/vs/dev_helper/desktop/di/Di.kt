package ru.vs.dev_helper.desktop.di

import org.kodein.di.DI
import ru.vs.core.adb.coreAdb
import ru.vs.core.di.Modules

fun createDi() = DI.lazy {
    importOnce(Modules.coreAdb())
}