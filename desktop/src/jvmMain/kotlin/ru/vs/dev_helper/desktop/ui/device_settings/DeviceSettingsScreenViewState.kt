package ru.vs.dev_helper.desktop.ui.device_settings

sealed class DeviceSettingsScreenViewState {
    object NoDevice : DeviceSettingsScreenViewState()
    data class DeviceConnected(val isShowViewBounds: Boolean) : DeviceSettingsScreenViewState()
}