package ru.vs.dev_helper.desktop.ui.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import ru.vs.core.adb.domain.AdbDeviceInteractor
import ru.vs.core.adb.domain.AdbDevicesInteractor
import ru.vs.core.decompose.view_model.ViewModel

class MainScreenViewModel(
    private val devicesInteractor: AdbDevicesInteractor,
    private val deviceInteractor: AdbDeviceInteractor,
) : ViewModel() {
    private val lastSelectedAdbDeviceSerial = MutableStateFlow<String?>(null)

    val viewState = combine(
        observeAdbDevices(),
        lastSelectedAdbDeviceSerial
    ) { adbDevices, serial ->
        val selectedDevice = adbDevices.find { it.device.serial == serial }
        MainScreenViewState(selectedDevice, adbDevices)
    }

    fun onSelectAdbDevice(device: MainScreenViewState.AdbDeviceViewState) {
        viewModelScope.launch {
            lastSelectedAdbDeviceSerial.emit(device.device.serial)
        }
    }

    private fun observeAdbDevices(): Flow<List<MainScreenViewState.AdbDeviceViewState>> {
        return devicesInteractor.observeDevices().mapLatest { devices ->
            devices.map { device ->
                val model = deviceInteractor.getDeviceModel(device)
                MainScreenViewState.AdbDeviceViewState(device, model)
            }
        }
    }
}