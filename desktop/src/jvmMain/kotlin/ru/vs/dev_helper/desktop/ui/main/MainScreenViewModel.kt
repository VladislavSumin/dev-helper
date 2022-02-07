package ru.vs.dev_helper.desktop.ui.main

import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.vs.core.adb.domain.AdbDeviceInteractor
import ru.vs.core.adb.domain.AdbDevicesInteractor
import ru.vs.core.decompose.view_model.ViewModel

class MainScreenViewModel(
    private val devicesInteractor: AdbDevicesInteractor,
    private val deviceInteractor: AdbDeviceInteractor,
) : ViewModel() {
    val viewState =
        devicesInteractor.observeDevices()
            .mapLatest { devices ->
                devices.map { device ->
                    val model = deviceInteractor.getDeviceModel(device)
                    MainScreenViewState.AdbDeviceViewState(device, model)
                }
            }
            .map { MainScreenViewState(null, it) }
}