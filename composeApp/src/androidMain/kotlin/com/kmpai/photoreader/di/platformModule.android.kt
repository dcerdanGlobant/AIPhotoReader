package com.kmpai.photoreader.di

import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerHomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule =
    module {
        viewModelOf(::PickerHomeViewModel)
    }
