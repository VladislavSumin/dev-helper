package ru.vs.core.adb.domain

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.request.prop.GetSinglePropRequest
import com.malinskiy.adam.request.shell.v2.ShellCommandRequest
import kotlinx.coroutines.CoroutineScope
import ru.vs.core.adb.data.AdbDevice

interface AdbDeviceInteractor {
    suspend fun getDeviceModel(device: AdbDevice): String
    suspend fun getIsShowViewBounds(device: AdbDevice): Boolean
    suspend fun setIsShowViewBounds(device: AdbDevice, value: Boolean)
}

internal class AdbDeviceInteractorImpl(
    private val adb: AndroidDebugBridgeClient,
    private val applicationScope: CoroutineScope
) : AdbDeviceInteractor {
    override suspend fun getDeviceModel(device: AdbDevice) = getProp(device, "ro.product.model")
    override suspend fun getIsShowViewBounds(device: AdbDevice) = getProp(device, "debug.layout").toBoolean()
    override suspend fun setIsShowViewBounds(device: AdbDevice, value: Boolean) =
        setProp(device, "debug.layout", value.toString())

    private suspend fun getProp(device: AdbDevice, propName: String): String {
        return adb.execute(GetSinglePropRequest(propName), serial = device.serial).trim()
    }

    private suspend fun setProp(device: AdbDevice, propName: String, propValue: String) {
        adb.execute(ShellCommandRequest("setprop $propName $propValue"), serial = device.serial)
    }
}