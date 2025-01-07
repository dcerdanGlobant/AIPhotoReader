package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.di.KoinDependency

object UseCasesProvider {

    fun getPictureDescriptionUseCase(): GetPictureDescription {
        return KoinDependency.koinApplication.koin.get()
    }

}