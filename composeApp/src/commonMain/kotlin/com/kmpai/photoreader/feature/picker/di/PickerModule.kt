package com.kmpai.photoreader.feature.picker.di

import com.kmpai.photoreader.feature.picker.data.PickerRepositoryImpl
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import org.koin.dsl.module

val pickerModule =
    module {
        factory<PickerRepository> { PickerRepositoryImpl() }
        single<GetPictureDescription> { GetPictureDescription(get())}
    }