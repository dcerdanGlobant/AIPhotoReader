package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.data.mappers.PictureMapper
import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.domain.model.Picture
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PickerAPIDatasource(
    private val restApi: RestApi,
    private val mapper: PictureMapper
) {

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getPictureDescription(extension: String, imageByteArray: ByteArray): Result<Picture> {
        try {
            val imageName = "${Uuid.random()}.$extension"
            val serverImageName = restApi.uploadImage(imageByteArray, imageName, "image/$extension")
            val response = restApi.requestImageDescription(serverImageName)
            return Result.success(mapper.map(response))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}