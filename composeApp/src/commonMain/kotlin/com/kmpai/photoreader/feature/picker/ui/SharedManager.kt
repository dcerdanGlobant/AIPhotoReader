package com.kmpai.photoreader.feature.picker.ui

import androidx.compose.runtime.Composable

@Composable
expect fun rememberSharedManager(onResult: (SharedImage?) -> Unit)