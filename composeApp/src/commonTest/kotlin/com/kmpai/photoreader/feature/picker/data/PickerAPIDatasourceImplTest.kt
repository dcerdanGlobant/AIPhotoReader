package com.kmpai.photoreader.feature.picker.data

import com.kmpai.photoreader.feature.picker.data.datasource.PickerAPIDatasourceImpl
import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.data.rest.SecretsUtils
import com.kmpai.photoreader.feature.picker.data.rest.model.ImageResponseBuilder
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.verify.VerifyMode.Companion.atMost
import dev.mokkery.verifySuspend
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PickerAPIDatasourceImplTest {

    @Test
    fun `should successfully get picture description`() = runBlocking {
        val httpClient = HttpClient()
        val restApi = RestApi(httpClient)
        val datasource = PickerAPIDatasourceImpl(restApi)
        val serverImageName = "uploaded_image.jpg"
        val imageResponse = ImageResponseBuilder().build()

        everySuspend { restApi.uploadImage(any(), any(), any()) } returns serverImageName
        everySuspend { restApi.requestImageDescription(serverImageName) } returns imageResponse
        every { SecretsUtils.getGlobantApiKey() } returns "globantAPIKey"

        val result = datasource.getPictureDescription("jpg", ByteArray(1))

        assertTrue(result.isSuccess)
        assertEquals("This is a picture description", result.getOrThrow().contentDescription)

        verifySuspend(atMost(1)) { //Call expected
            restApi.uploadImage(any(), any(), any())
            restApi.requestImageDescription(any())
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

        val result = datasource.getPictureDescription("jpg", ByteArray(1))

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
        everySuspend { restApi.requestImageDescription(serverImageName) } throws RuntimeException("Description request failed")

        val result = datasource.getPictureDescription("jpg", ByteArray(1))

        assertTrue(result.isFailure)
        verifySuspend(atMost(1)) { //Call expected
            restApi.uploadImage(any(), any(), any())
            SecretsUtils.getGlobantApiKey()
        }
    }
}