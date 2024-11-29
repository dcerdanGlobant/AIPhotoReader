package com.kmpai.photoreader.di

import com.kmpai.photoreader.feature.picker.di.pickerModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(platformModule, pickerModule)
    }
}