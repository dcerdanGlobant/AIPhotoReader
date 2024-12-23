package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.data.rest.mappers.firstMessage
import com.kmpai.photoreader.feature.picker.data.rest.mappers.toAPIMessages
import com.kmpai.photoreader.feature.picker.data.rest.mappers.toConversation
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AIAPIDatasourceImpl(
    private val restApi: RestApi,
) : AIDatasource {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun sendImageAndStartConversation(
        extension: String,
        imageByteArray: ByteArray
    ): Result<Conversation> {
        try {
            val imageName = "${Uuid.random()}.$extension"
            val serverImageName = restApi.uploadImage(imageByteArray, imageName, "image/$extension")
            val response = restApi.sendMessagesToAI(listOf(serverImageName.firstMessage()))
            return Result.success(response.toConversation(serverImageName))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun sendConversation(conversation: Conversation): Result<Conversation> {
        val response = restApi.sendMessagesToAI(conversation.toAPIMessages())
        return Result.success(response.toConversation(conversation))
    }
}
