package com.kmpai.photoreader.feature.picker.di

import com.kmpai.photoreader.core.framework.network.httpClient
import com.kmpai.photoreader.feature.picker.data.datasource.PickerAPIDatasourceImpl
import com.kmpai.photoreader.feature.picker.data.repository.PickerRepositoryImpl
import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.data.language.LanguageHelper
import com.kmpai.photoreader.feature.picker.data.language.LanguageHelperImpl
import com.kmpai.photoreader.feature.picker.data.rest.RestApiInterface
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import com.kmpai.photoreader.feature.picker.domain.usecase.SendConversation
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val pickerModule =
    module {
        single { httpClient }
        singleOf(::RestApi).bind<RestApiInterface>()
        singleOf(::LanguageHelperImpl).bind<LanguageHelper>()
        singleOf(::PickerAPIDatasourceImpl).bind<PickerDatasource>()
        singleOf(::PickerRepositoryImpl).bind<PickerRepository>()
        singleOf(::GetPictureDescription)
        singleOf(::SendConversation)
    }
