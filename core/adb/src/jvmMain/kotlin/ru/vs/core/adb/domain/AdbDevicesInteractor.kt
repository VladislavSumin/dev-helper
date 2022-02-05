package ru.vs.core.adb.domain

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.request.device.AsyncDeviceMonitorRequest
import com.malinskiy.adam.request.device.Device
import com.malinskiy.adam.request.device.DeviceState
import com.malinskiy.adam.request.device.ListDevicesRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import ru.vs.core.adb.data.AdbDevice
import ru.vs.core.adb.data.AdbDeviceState

interface AdbDevicesInteractor {
    suspend fun getDeviceList(): List<AdbDevice>
    fun observeDevices(): Flow<List<AdbDevice>>
}

internal class AdbDevicesInteractorImpl(
    private val adb: AndroidDebugBridgeClient,
    private val applicationScope: CoroutineScope
) : AdbDevicesInteractor {
    override suspend fun getDeviceList(): List<AdbDevice> {
        return adb.execute(ListDevicesRequest()).toAdbDevices()
    }

    override fun observeDevices(): Flow<List<AdbDevice>> {
        //TODO make shared
        return adb.execute(AsyncDeviceMonitorRequest(), applicationScope)
            .consumeAsFlow()
            .map { it.toAdbDevices() }
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