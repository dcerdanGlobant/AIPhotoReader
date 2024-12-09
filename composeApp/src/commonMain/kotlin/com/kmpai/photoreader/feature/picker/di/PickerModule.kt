package com.kmpai.photoreader.feature.picker.di

import com.kmpai.photoreader.core.framework.network.httpClient
import com.kmpai.photoreader.feature.picker.data.datasource.PickerAPIDatasourceImpl
import com.kmpai.photoreader.feature.picker.data.repository.PickerRepositoryImpl
import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module



val pickerModule =
    module {
        single { httpClient }
        singleOf(::RestApi)
        singleOf(::PickerAPIDatasourceImpl).bind<PickerDatasource>()
        singleOf(::PickerRepositoryImpl).bind<PickerRepository>()
        singleOf(::GetPictureDescription)
    }

