package com.kmpai.photoreader.di

import com.kmpai.photoreader.feature.picker.data.datasource.LocalImageDatasourceImpl
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerHomeViewModel
import com.kmpai.photoreader.feature.picker.domain.datasource.LocalImageDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

actual val platformModule =
    module {
        viewModelOf(::PickerHomeViewModel)

        single<LocalImageDataSource> {  LocalImageDatasourceImpl(androidContext()) }
    }