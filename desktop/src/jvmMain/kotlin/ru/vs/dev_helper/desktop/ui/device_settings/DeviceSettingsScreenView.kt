package ru.vs.dev_helper.desktop.ui.device_settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import ru.vs.core.decompose.view_model.decomposeViewModel
import ru.vs.dev_helper.desktop.ui.device_settings.DeviceSettingsScreenViewState as State

@Composable
fun DeviceSettingsScreenView() {
    val viewModel = decomposeViewModel<DeviceSettingsScreenViewModel>()
    val state = viewModel.state.collectAsState(null).value ?: return

    Surface(Modifier.fillMaxSize()) {
        when (state) {
            is State.NoDevice -> /* no action */ Unit
            is State.DeviceConnected -> RenderConnectedState(
                state,
                viewModel::onClickBooleanMenuItem,
            )
        }
    }
}

@Composable
private fun RenderConnectedState(
    state: State.DeviceConnected,
    onChangeItemStateListener: (State.MenuItemId, Boolean) -> Unit
) {
    LazyColumn {
        items(state.menuItems, { it.id }) { menuItem ->
            when (menuItem) {
                is State.MenuItem.LoadingItem -> RenderLoadingItem(menuItem)
                is State.MenuItem.BooleanItem -> RenderBooleanMenuItem(menuItem) {
                    onChangeItemStateListener(menuItem.id, it)
                }
            }
        }
    }
}

@Composable
private fun RenderLoadingItem(
    item: State.MenuItem.LoadingItem
) {
    RenderMenuItem(item) {
        CircularProgressIndicator(strokeWidth = 1.dp, modifier = Modifier.padding(16.dp).size(16.dp))
    }
}

@Composable
private fun RenderBooleanMenuItem(
    item: State.MenuItem.BooleanItem,
    onChangeItemStateListener: (Boolean) -> Unit
) {
    RenderMenuItem(item) {
        Checkbox(item.value, onChangeItemStateListener)
    }
}

@Composable
private fun RenderMenuItem(item: State.MenuItem, block: @Composable RowScope.() -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp, 8.dp)
    ) {
        Text(stringResource(item.title))
        block()
    }
}