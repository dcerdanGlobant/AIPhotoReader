package com.kmpai.photoreader.di

import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerHomeViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

actual val platformModule =
    module {
        viewModelOf(::PickerHomeViewModel)
    }