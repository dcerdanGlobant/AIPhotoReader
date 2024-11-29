package com.kmpai.photoreader.navigation

import org.jetbrains.compose.resources.StringResource

data class ScreenNavigatorConfig(
    val route: String,
    val title: StringResource? = null,
)