package com.kmpai.photoreader

import androidx.compose.ui.window.ComposeUIViewController
import com.kmpai.photoreader.core.ui.App
import com.kmpai.photoreader.di.initKoin
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App(
        darkTheme = UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark,
        dynamicColor = false,
    )
}