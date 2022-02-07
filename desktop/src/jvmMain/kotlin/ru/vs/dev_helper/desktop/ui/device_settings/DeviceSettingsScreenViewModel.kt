package ru.vs.dev_helper.desktop.ui.device_settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.vs.core.adb.data.AdbDevice
import ru.vs.core.adb.domain.AdbDeviceInteractor
import ru.vs.core.decompose.view_model.ViewModel
import ru.vs.dev_helper.desktop.ui.main.MainScreenViewModel

class DeviceSettingsScreenViewModel(
    private val adbDeviceInteractor: AdbDeviceInteractor,
    private val mainScreenViewModel: MainScreenViewModel,
) : ViewModel() {
    private val updateStateEvent = MutableSharedFlow<Unit>()
    val state: Flow<DeviceSettingsScreenViewState> =
        combine(
            mainScreenViewModel.selectedAdbDevice,
            updateStateEvent.onStart { emit(Unit) }
        ) { device, _ -> device }
            .mapLatest { device ->
                if (device != null) {
                    getDeviceViewState(device)
                } else {
                    DeviceSettingsScreenViewState.NoDevice
                }
            }

    fun onSetIsShowViewBounds(value: Boolean) {
        viewModelScope.launch {
            val device = mainScreenViewModel.selectedAdbDevice.first() ?: return@launch
            adbDeviceInteractor.setIsShowViewBounds(device, value)
            updateStateEvent.emit(Unit)
        }
    }

    private suspend fun getDeviceViewState(device: AdbDevice): DeviceSettingsScreenViewState.DeviceConnected {
        return DeviceSettingsScreenViewState.DeviceConnected(
            isShowViewBounds = adbDeviceInteractor.getIsShowViewBounds(device)
        )
    }
}