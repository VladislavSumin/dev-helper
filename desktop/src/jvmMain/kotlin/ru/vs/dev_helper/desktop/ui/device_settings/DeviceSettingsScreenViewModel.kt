package ru.vs.dev_helper.desktop.ui.device_settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import ru.vs.core.adb.data.AdbDevice
import ru.vs.core.adb.domain.AdbDeviceInteractor
import ru.vs.core.decompose.view_model.ViewModel
import ru.vs.dev_helper.desktop.ui.main.MainScreenViewModel

class DeviceSettingsScreenViewModel(
    private val adbDeviceInteractor: AdbDeviceInteractor,
    private val mainScreenViewModel: MainScreenViewModel,
) : ViewModel() {
    val state: Flow<DeviceSettingsScreenViewState> = mainScreenViewModel.selectedAdbDevice
        .mapLatest { device ->
            if (device != null) {
                getDeviceViewState(device)
            } else {
                DeviceSettingsScreenViewState.NoDevice
            }
        }

    private suspend fun getDeviceViewState(device: AdbDevice): DeviceSettingsScreenViewState.DeviceConnected {
        return DeviceSettingsScreenViewState.DeviceConnected(
            isShowViewBounds = adbDeviceInteractor.getIsShowViewBounds(device)
        )
    }
}