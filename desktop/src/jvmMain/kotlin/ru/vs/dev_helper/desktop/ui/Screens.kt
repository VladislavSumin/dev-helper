package ru.vs.dev_helper.desktop.ui

import androidx.compose.runtime.Composable
import ru.vs.core.navigation.Screen
import ru.vs.dev_helper.desktop.ui.main.MainScreenView


object MainScreen : Screen {
    @Composable
    override fun ScreenView() {
        MainScreenView()
    }
}
