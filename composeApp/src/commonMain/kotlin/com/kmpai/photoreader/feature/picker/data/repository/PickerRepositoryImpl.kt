package com.kmpai.photoreader.feature.picker.data.repository

import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class PickerRepositoryImpl(private val datasource: PickerDatasource): PickerRepository {
    override suspend fun getPictureDescription(extension: String, imageByteArray: ByteArray): Result<Picture> {
        return datasource.getPictureDescription(extension, imageByteArray)
    }
}
