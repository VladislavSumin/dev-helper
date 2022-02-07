package ru.vs.core.adb.domain

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.request.device.AsyncDeviceMonitorRequest
import com.malinskiy.adam.request.device.Device
import com.malinskiy.adam.request.device.DeviceState
import com.malinskiy.adam.request.device.ListDevicesRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
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
    private val devicesObservable =
        flow { emit(adb.execute(AsyncDeviceMonitorRequest(), applicationScope)) }
            .flatMapLatest { it.consumeAsFlow() }
            .map { it.toAdbDevices() }
            .shareIn(
                applicationScope,
                replay = 1,
                started = SharingStarted.WhileSubscribed(3000, 0)
            )

    override suspend fun getDeviceList(): List<AdbDevice> {
        return adb.execute(ListDevicesRequest()).toAdbDevices()
    }

    override fun observeDevices(): Flow<List<AdbDevice>> = devicesObservable
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