package ru.vs.dev_helper.desktop.ui.top_bar

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import ru.vs.core.adb.domain.AdbDeviceInteractor
import ru.vs.core.adb.domain.AdbDevicesInteractor
import ru.vs.core.decompose.view_model.ViewModel
import ru.vs.dev_helper.desktop.ui.main.MainScreenViewModel

class TopBarScreenViewModel(
    private val devicesInteractor: AdbDevicesInteractor,
    private val deviceInteractor: AdbDeviceInteractor,
    private val mainViewModel: MainScreenViewModel
) : ViewModel() {
    val viewState = combine(
        observeAdbDevices(),
        mainViewModel.selectedAdbDevice
    ) { adbDevices, selected ->
        val selectedDevice = adbDevices.find { it.device.serial == selected?.serial }
        TopBarScreenViewState(selectedDevice, adbDevices)
    }

    fun onSelectAdbDevice(device: TopBarScreenViewState.AdbDeviceViewState) {
        mainViewModel.setSelectedAdbDevice(device.device.serial)
    }

    private fun observeAdbDevices(): Flow<List<TopBarScreenViewState.AdbDeviceViewState>> {
        return devicesInteractor.observeDevices().mapLatest { devices ->
            devices.map { device ->
                val model = deviceInteractor.getDeviceModel(device)
                TopBarScreenViewState.AdbDeviceViewState(device, model)
            }
        }
    }
}