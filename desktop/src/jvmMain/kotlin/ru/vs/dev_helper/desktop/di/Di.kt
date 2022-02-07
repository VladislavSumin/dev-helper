package ru.vs.dev_helper.desktop.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import ru.vs.core.adb.coreAdb
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.dev_helper.desktop.ui.device_settings.DeviceSettingsScreenViewModel
import ru.vs.dev_helper.desktop.ui.main.MainScreenView
import ru.vs.dev_helper.desktop.ui.main.MainScreenViewModel
import ru.vs.dev_helper.desktop.ui.top_bar.TopBarScreenViewModel

fun createDi() = DI.lazy {
    importOnce(Modules.coreAdb())

    bindProvider { DeviceSettingsScreenViewModel(i(), i()) }
    bindProvider { MainScreenViewModel(i()) }
    bindProvider { TopBarScreenViewModel(i(), i(), i()) }
}