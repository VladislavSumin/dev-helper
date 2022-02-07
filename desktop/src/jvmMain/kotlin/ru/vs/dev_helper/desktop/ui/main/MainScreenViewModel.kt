package ru.vs.dev_helper.desktop.ui.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.vs.core.adb.domain.AdbDevicesInteractor
import ru.vs.core.decompose.view_model.ViewModel

class MainScreenViewModel(
    devicesInteractor: AdbDevicesInteractor
) : ViewModel() {
    // keep device info when selected device disconnected
    private val lastSelectedAdbDeviceSerial = MutableStateFlow<String?>(null)

    val selectedAdbDevice = combine(
        devicesInteractor.observeDevices(),
        lastSelectedAdbDeviceSerial
    ) { devices, serial ->
        devices.find { it.serial == serial }
    }.shareIn(viewModelScope, started = SharingStarted.Lazily)

    fun setSelectedAdbDevice(deviceId: String) {
        viewModelScope.launch {
            lastSelectedAdbDeviceSerial.emit(deviceId)
        }
    }
}