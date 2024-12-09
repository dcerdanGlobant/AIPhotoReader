package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.data.rest.mappers.toPictureModel
import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.domain.model.Picture
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PickerAPIDatasourceImpl(
    private val restApi: RestApi,
) : PickerDatasource {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getPictureDescription(extension: String, imageByteArray: ByteArray): Result<Picture> {
        try {
            val imageName = "${Uuid.random()}.$extension"
            val serverImageName = restApi.uploadImage(imageByteArray, imageName, "image/$extension")
            val response = restApi.requestImageDescription(serverImageName)
            return Result.success(response.toPictureModel())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}