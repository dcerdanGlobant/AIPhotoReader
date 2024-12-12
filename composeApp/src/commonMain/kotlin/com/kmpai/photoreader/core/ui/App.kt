package com.kmpai.photoreader.core.ui

import androidx.compose.runtime.*
import com.kmpai.photoreader.core.navigation.ScreenNavigator
import core.ui.theme.AIPhotoReaderTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    AIPhotoReaderTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        KoinContext {
            ScreenNavigator()
        }
    }
}