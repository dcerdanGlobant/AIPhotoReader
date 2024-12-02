package com.kmpai.photoreader.feature.picker.data.repository

import com.kmpai.photoreader.feature.picker.domain.datasource.LocalImageDataSource
import com.kmpai.photoreader.feature.picker.domain.repository.ImageRepository

class LocalImageRepository(private val imageDataSource: LocalImageDataSource): ImageRepository {
    override suspend fun getImage(filename: String): ByteArray? {
       return imageDataSource.getImageByteArray(filename)
    }
}