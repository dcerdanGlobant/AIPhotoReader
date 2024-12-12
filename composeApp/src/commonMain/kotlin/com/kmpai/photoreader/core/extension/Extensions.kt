package com.kmpai.photoreader.core.extension

import com.kmpai.photoreader.core.navigation.ScreenNavigatorConfig
import com.kmpai.photoreader.core.ui.TopBarConfig

internal fun ScreenNavigatorConfig.toTopBarConfig(): TopBarConfig =
    TopBarConfig(
        title = title,
        backAllowed = topBarBackEnabled,
    )