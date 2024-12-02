package com.kmpai.photoreader.di

import com.kmpai.photoreader.feature.picker.data.datasource.LocalImageDatasourceImpl
import com.kmpai.photoreader.feature.picker.domain.datasource.LocalImageDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerHomeViewModel

actual val platformModule =
    module {
        singleOf(::PickerHomeViewModel)
        single<LocalImageDataSource> {  LocalImageDatasourceImpl() }
    }