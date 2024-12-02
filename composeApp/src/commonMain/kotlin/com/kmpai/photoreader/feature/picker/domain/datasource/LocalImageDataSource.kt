package com.kmpai.photoreader.feature.picker.domain.datasource

interface LocalImageDataSource {

    suspend fun getImageByteArray(filename: String): ByteArray?
}
