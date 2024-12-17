package com.kmpai.photoreader.di

import com.kmpai.photoreader.feature.picker.di.pickerModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.core.KoinApplication

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(platformModule, pickerModule)
    }
}

object KoinProvider {
    val koinApplication: KoinApplication by lazy {
        startKoin {
            modules(platformModule, pickerModule)
        }
    }
}