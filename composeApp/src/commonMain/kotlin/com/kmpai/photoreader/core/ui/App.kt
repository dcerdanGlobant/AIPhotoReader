package com.kmpai.photoreader.core.ui


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.kmpai.photoreader.core.navigation.ScreenNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            ScreenNavigator()
        }
    }
}