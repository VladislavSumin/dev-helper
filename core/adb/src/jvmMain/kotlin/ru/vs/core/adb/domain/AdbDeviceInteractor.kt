package ru.vs.core.adb.domain

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.request.device.Device
import com.malinskiy.adam.request.prop.GetSinglePropRequest
import kotlinx.coroutines.CoroutineScope
import ru.vs.core.adb.data.AdbDevice

interface AdbDeviceInteractor {
    suspend fun getDeviceModel(device: AdbDevice): String
}

internal class AdbDeviceInteractorImpl(
    private val adb: AndroidDebugBridgeClient,
    private val applicationScope: CoroutineScope
) : AdbDeviceInteractor {
    override suspend fun getDeviceModel(device: AdbDevice): String {
        return adb.execute(GetSinglePropRequest("ro.product.model"), serial = device.serial)
    }
}