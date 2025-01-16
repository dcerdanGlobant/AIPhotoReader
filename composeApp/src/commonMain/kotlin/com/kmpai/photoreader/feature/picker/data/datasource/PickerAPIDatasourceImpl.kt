package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.data.rest.mappers.toAPIMessages
import com.kmpai.photoreader.feature.picker.data.rest.mappers.appendConversation
import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PickerAPIDatasourceImpl(
    private val restApi: RestApi,
) : PickerDatasource {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun sendImageAndStartConversation(
        extension: String,
        imageByteArray: ByteArray
    ): Flow<CommonResult<Conversation>> {
        return flow {
            try {
                val imageName = "${Uuid.random()}.$extension"
                val serverImageName = restApi.uploadImage(imageByteArray, imageName, "image/$extension")
                val conversation = Conversation(emptyList(), serverImageName)
                return@flow emit(sended(conversation))
            } catch (e: Exception) {
                return@flow emit(CommonResult.Failure(e))
            }
        }
    }

    override suspend fun sendConversation(conversation: Conversation): Result<Conversation> {
        return try {
            send(conversation)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun send(conversation: Conversation): Result<Conversation> {
        val response = restApi.sendMessagesToAI(conversation.toAPIMessages())
        return Result.success(conversation.appendConversation(response))
    }

    private suspend fun sended(conversation: Conversation): CommonResult<Conversation> {
        val response = restApi.sendMessagesToAI(conversation.toAPIMessages())
        return CommonResult.Success(conversation.appendConversation(response))
    }
}
