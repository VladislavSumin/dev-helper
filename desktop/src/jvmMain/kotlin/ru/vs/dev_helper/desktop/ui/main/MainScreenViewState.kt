package ru.vs.dev_helper.desktop.ui.main

import ru.vs.core.adb.data.AdbDevice


data class MainScreenViewState(
    val selectedDevice: AdbDeviceViewState?,
    val adbDevices: List<AdbDeviceViewState>
) {
    data class AdbDeviceViewState(
        val device: AdbDevice,
        val model: String
    )
}