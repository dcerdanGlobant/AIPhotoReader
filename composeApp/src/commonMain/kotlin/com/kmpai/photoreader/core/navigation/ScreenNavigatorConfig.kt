package com.kmpai.photoreader.core.navigation

import org.jetbrains.compose.resources.StringResource

data class ScreenNavigatorConfig(
    val route: String,
    val title: StringResource? = null,
    val topBarBackEnabled: Boolean = false,
)