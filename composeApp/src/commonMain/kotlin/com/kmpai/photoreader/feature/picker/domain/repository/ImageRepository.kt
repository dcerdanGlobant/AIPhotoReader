package com.kmpai.photoreader.feature.picker.domain.repository

interface ImageRepository {
    suspend fun getImage(filename: String): ByteArray?
}
