package ru.vs.core.adb.domain

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.request.device.Device
import com.malinskiy.adam.request.device.DeviceState
import com.malinskiy.adam.request.device.ListDevicesRequest
import ru.vs.core.adb.data.AdbDevice
import ru.vs.core.adb.data.AdbDeviceState

interface AdbDevicesInteractor {
    suspend fun getDeviceList(): List<AdbDevice>
}

internal class AdbDevicesInteractorImpl(
    private val adb: AndroidDebugBridgeClient
) : AdbDevicesInteractor {
    override suspend fun getDeviceList(): List<AdbDevice> {
        return adb.execute(ListDevicesRequest()).toAdbDevices()
    }
}

private fun List<Device>.toAdbDevices() = map(Device::toAdbDevice)

private fun Device.toAdbDevice() = AdbDevice(serial, state.toAdbState())

private fun DeviceState.toAdbState() = when (this) {
    DeviceState.OFFLINE -> AdbDeviceState.OFFLINE
    DeviceState.DEVICE -> AdbDeviceState.DEVICE
    DeviceState.BOOTLOADER,
    DeviceState.HOST,
    DeviceState.RECOVERY,
    DeviceState.RESCUE,
    DeviceState.SIDELOAD,
    DeviceState.UNAUTHORIZED,
    DeviceState.AUTHORIZING,
    DeviceState.CONNECTING -> AdbDeviceState.OTHER
    DeviceState.UNKNOWN -> AdbDeviceState.UNKNOWN
}