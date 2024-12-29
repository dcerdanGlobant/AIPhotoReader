package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.data.rest.SecretsUtils
import com.kmpai.photoreader.feature.picker.data.rest.model.ApiResponseBuilder
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode.Companion.atMost
import dev.mokkery.verifySuspend
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PickerAPIDatasourceImplTest {

    /*@Test
    fun `sendImageAndStartConversation should successfully get a conversation`() = runBlocking {

        val httpClient = HttpClient()
        val restApi = RestApi(httpClient)
        val datasource = PickerAPIDatasourceImpl(restApi)
        val serverImageName = "uploaded_image.jpg"
        val apiResponse = ApiResponseBuilder().build()

        everySuspend { restApi.uploadImage(any(), any(), any()) } returns serverImageName
        everySuspend { restApi.sendMessagesToAI(any()) } returns apiResponse
        every { SecretsUtils.getGlobantApiKey() } returns "globantAPIKey"

        val result = datasource.sendImageAndStartConversation("jpg", ByteArray(1))

        assertTrue(result.isSuccess)
        assertEquals(
            Result.success(
                Conversation(
                    messages = emptyList(),
                    filename = "uploaded_image.jpg"
                )
            ), result
        )

        verifySuspend(atMost(1)) { //Call expected
            restApi.uploadImage(any(), any(), any())
            restApi.sendMessagesToAI(any())
        }
        verify {
            SecretsUtils.getGlobantApiKey()
        }
    }

    @Test
    fun `should return failure when uploadImage fails`() = runBlocking {
        val httpClient = HttpClient()
        val restApi = RestApi(httpClient)
        val datasource = PickerAPIDatasourceImpl(restApi)

        every { SecretsUtils.getGlobantApiKey() } returns "globantAPIKey"
        everySuspend {
            restApi.uploadImage(
                any(),
                any(),
                any()
            )
        } throws RuntimeException("Upload failed")

        val result = datasource.sendImageAndStartConversation("jpg", ByteArray(1))

        assertTrue(result.isFailure)
        verifySuspend(atMost(1)) { //Call expected
            restApi.uploadImage(any(), any(), any())
            SecretsUtils.getGlobantApiKey()
        }
    }

    @Test
    fun `should return failure when requestImageDescription fails`() = runBlocking {
        val httpClient = HttpClient()
        val restApi = RestApi(httpClient)
        val datasource = PickerAPIDatasourceImpl(restApi)
        val serverImageName = "uploaded_image.jpg"

        every { SecretsUtils.getGlobantApiKey() } returns "globantAPIKey"
        everySuspend { restApi.uploadImage(any(), any(), any()) } returns serverImageName
        everySuspend { restApi.sendMessagesToAI(any()) } throws RuntimeException("Description request failed")

        val result = datasource.sendImageAndStartConversation("jpg", ByteArray(1))

        assertTrue(result.isFailure)
        verifySuspend(atMost(1)) { //Call expected
            restApi.uploadImage(any(), any(), any())
            SecretsUtils.getGlobantApiKey()
        }
    }*/
}