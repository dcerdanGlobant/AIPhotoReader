package com.kmpai.photoreader.feature.picker.di

import com.kmpai.photoreader.feature.picker.data.datasource.PickerAPIDatasource
import com.kmpai.photoreader.feature.picker.data.mappers.PictureMapper
import com.kmpai.photoreader.feature.picker.data.repository.PickerRepositoryImpl
import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.data.rest.model.ImageRequestFactory
import com.kmpai.photoreader.feature.picker.domain.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val client = HttpClient {
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println("HTTP Client: $message")
            }
        }
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}

val pickerModule =
    module {
        single<ImageRequestFactory> { ImageRequestFactory() }
        single<RestApi> { RestApi(get(), client) }
        single<PictureMapper> { PictureMapper() }
        factory<PickerDatasource> { PickerAPIDatasource(get(), get()) }
        factory<PickerRepository> { PickerRepositoryImpl(get()) }
        single<GetPictureDescription> { GetPictureDescription(get())}
    }

