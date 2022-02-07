package ru.vs.dev_helper.desktop.ui.main

import androidx.compose.runtime.Composable
import org.kodein.di.bindProvider
import org.kodein.di.compose.subDI
import ru.vs.core.decompose.view_model.decomposeViewModel
import ru.vs.dev_helper.desktop.ui.top_bar.TopBarScreenView

@Composable
fun MainScreenView() {
    val viewModel = decomposeViewModel<MainScreenViewModel>()
    subDI(
        diBuilder = {
            bindProvider(overrides = true) { viewModel }
        }
    ) {
        TopBarScreenView()
    }
}

