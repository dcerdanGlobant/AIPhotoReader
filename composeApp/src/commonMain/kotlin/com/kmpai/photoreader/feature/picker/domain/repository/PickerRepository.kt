package com.kmpai.photoreader.feature.picker.domain.repository

import com.kmpai.photoreader.feature.picker.domain.model.Picture

interface PickerRepository {
    suspend fun getPictureDescription(filename: String, imageByteArray: ByteArray): Result<Picture>
}