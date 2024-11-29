package com.kmpai.photoreader.ui


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.KoinContext

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            ScreenNavigator()
        }
    }
}