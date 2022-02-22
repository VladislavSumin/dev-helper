package ru.vs.dev_helper.desktop.ui.device_settings

import dev.icerock.moko.resources.StringResource

sealed class DeviceSettingsScreenViewState {
    object NoDevice : DeviceSettingsScreenViewState()

    data class DeviceConnected(
        val menuItems: List<MenuItem>
    ) : DeviceSettingsScreenViewState()

    sealed interface MenuItem {
        val id: MenuItemId
        val title: StringResource

        data class LoadingItem(
            override val id: MenuItemId,
            override val title: StringResource
        ) : MenuItem {
            fun toBooleanState(value: Boolean) = BooleanItem(id, title, value)
        }

        data class BooleanItem(
            override val id: MenuItemId,
            override val title: StringResource,
            val value: Boolean
        ) : MenuItem

        fun toLoadingState() = LoadingItem(id, title)
    }

    enum class MenuItemId {
        SHOW_VIEW_BOUNDS
    }
}