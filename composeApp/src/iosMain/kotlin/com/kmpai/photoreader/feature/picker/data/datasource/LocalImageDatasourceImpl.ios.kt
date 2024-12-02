package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.domain.datasource.LocalImageDataSource

actual class LocalImageDatasourceImpl : LocalImageDataSource {

    override suspend fun getImageByteArray(filename: String): ByteArray? {
        return null //TODO
    }
}