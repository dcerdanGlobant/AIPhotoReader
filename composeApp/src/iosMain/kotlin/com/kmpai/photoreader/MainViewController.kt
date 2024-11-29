package com.kmpai.photoreader

import androidx.compose.ui.window.ComposeUIViewController
import com.kmpai.photoreader.di.initKoin
import com.kmpai.photoreader.core.ui.App

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin()}
) { App() }