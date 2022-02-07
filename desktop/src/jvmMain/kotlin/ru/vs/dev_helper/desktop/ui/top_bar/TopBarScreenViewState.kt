package ru.vs.dev_helper.desktop.ui.top_bar

import ru.vs.core.adb.data.AdbDevice

data class TopBarScreenViewState(
    val selectedDevice: AdbDeviceViewState?,
    val adbDevices: List<AdbDeviceViewState>
) {
    data class AdbDeviceViewState(
        val device: AdbDevice,
        val model: String
    )
}