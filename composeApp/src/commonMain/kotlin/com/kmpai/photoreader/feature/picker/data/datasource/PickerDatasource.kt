package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.domain.model.Picture

interface PickerDatasource {

    suspend fun getPictureDescription(extension: String, imageByteArray: ByteArray): Result<Picture>
}
