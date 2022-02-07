package ru.vs.dev_helper.desktop.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ui.LocalComponentContextHolder
import ru.vs.core.navigation.ui.LocalRootNavigationHolder
import ru.vs.core.navigation.ui.NavigationContentView
import ru.vs.core.navigation.ui.defaultRouter
import ru.vs.core.uikit.theme.MainTheme
import ru.vs.dev_helper.desktop.ui.MainScreen

@Composable
fun RootScreenView(componentContext: ComponentContext) {
    val router = remember {
        componentContext.defaultRouter(MainScreen, "rootRouter")
    }

    LocalComponentContextHolder(componentContext) {
        MainTheme(darkTheme = true) {
            Surface(Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                LocalRootNavigationHolder(router) {
                    NavigationContentView()
                }
            }
        }
    }
}
