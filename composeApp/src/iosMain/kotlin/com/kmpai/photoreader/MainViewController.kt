package com.kmpai.photoreader

import androidx.compose.ui.window.ComposeUIViewController
import com.kmpai.photoreader.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin()}
) { App() }