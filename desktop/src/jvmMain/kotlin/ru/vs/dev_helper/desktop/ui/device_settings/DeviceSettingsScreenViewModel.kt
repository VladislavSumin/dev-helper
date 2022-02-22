package ru.vs.dev_helper.desktop.ui.device_settings

import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.vs.core.adb.data.AdbDevice
import ru.vs.core.adb.domain.AdbDeviceInteractor
import ru.vs.core.decompose.view_model.ViewModel
import ru.vs.dev_helper.MR
import ru.vs.dev_helper.desktop.ui.main.MainScreenViewModel
import ru.vs.dev_helper.desktop.ui.device_settings.DeviceSettingsScreenViewState as State

class DeviceSettingsScreenViewModel(
    private val adbDeviceInteractor: AdbDeviceInteractor,
    private val mainScreenViewModel: MainScreenViewModel,
) : ViewModel() {
    val state: Flow<State> = mainScreenViewModel.selectedAdbDevice.flatMapLatest { device ->
        if (device != null) observeConnectedState(device)
        else flowOf(State.NoDevice)
    }

    private val settingsChangeRequestChannel = Channel<ChangeStateRequest>(capacity = Channel.RENDEZVOUS)

    fun onClickBooleanMenuItem(id: State.MenuItemId, newState: Boolean) {
        viewModelScope.launch {
            settingsChangeRequestChannel.send(ChangeStateRequest(id, newState))
        }
    }

    private fun observeConnectedState(device: AdbDevice): Flow<State.DeviceConnected> {
        return observeSettingsState(device).map { State.DeviceConnected(it) }
    }

    private fun observeSettingsState(device: AdbDevice): Flow<List<State.MenuItem>> = flow {
        var settingsState = getInitialSettingsState()
        emit(settingsState)

        val initialLoad = requestStates(device, settingsState.filterIsInstance<State.MenuItem.LoadingItem>())
        val byUserUpdated = settingsChangeRequestChannel.consumeAsFlow()
            .transform { changeRequest ->
                val oldState = settingsState.find { it.id == changeRequest.id }!!
                emit(oldState.toLoadingState())

                //TODO тут поддержать разные запросы
                //todo добавить when по id'шкам
                adbDeviceInteractor.setIsShowViewBounds(device, changeRequest.newState)
                val checkedResult = adbDeviceInteractor.getIsShowViewBounds(device)
                emit(oldState.toLoadingState().toBooleanState(checkedResult))
            }

        merge(initialLoad, byUserUpdated).collect { newState ->
            val newStateList = settingsState.toMutableList()
            val index = newStateList.indexOfFirst { it.id == newState.id }
            newStateList[index] = newState
            settingsState = newStateList
            emit(newStateList)
        }
    }

    private suspend fun requestStates(device: AdbDevice, states: List<State.MenuItem.LoadingItem>) = flow {
        states.forEach { emit(requestState(device, it)) }
    }

    private suspend fun requestState(device: AdbDevice, state: State.MenuItem.LoadingItem): State.MenuItem {
        return when (state.id) {
            State.MenuItemId.SHOW_VIEW_BOUNDS -> state.toBooleanState(adbDeviceInteractor.getIsShowViewBounds(device))
        }
    }

    private fun getInitialSettingsState(): List<State.MenuItem> {
        return State.MenuItemId.values().map {
            State.MenuItem.LoadingItem(it, it.titleRes)
        }
    }
}

private data class ChangeStateRequest(val id: State.MenuItemId, val newState: Boolean)

private val State.MenuItemId.titleRes: StringResource
    get() = when (this) {
        State.MenuItemId.SHOW_VIEW_BOUNDS -> MR.strings.device_settings_show_view_bounds
    }