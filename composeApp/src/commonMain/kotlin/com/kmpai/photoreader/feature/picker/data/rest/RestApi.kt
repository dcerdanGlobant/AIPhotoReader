package com.kmpai.photoreader.feature.picker.data.rest

import com.kmpai.photoreader.feature.picker.data.rest.model.APIRequest
import com.kmpai.photoreader.feature.picker.data.rest.model.APIResponse
import com.kmpai.photoreader.feature.picker.data.rest.model.APIMessage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class RestApi(private val client: HttpClient) {

    companion object {
        private const val ORGANIZATION = "ae3a4928-505c-40c7-b3bc-c758a217d59a"
        private const val PROJECT_ID = "36ab643f-42bb-4801-b5a6-4eeb938c376d"
        const val ASSISTANT_CHAT_URL = "https://api.saia.ai/chat"
        const val FILE_UPLOAD_URL = "https://api.saia.ai/v1/files"
        private const val ORGANIZATION_HEADER = "organizationId"
        private const val PROJECT_ID_HEADER = "projectId"
        private const val FILE_NAME_HEADER = "fileName"
        private const val ACCEPT_HEADER = "Accept"
        private const val ACCEPT_HEADER_VALUE = "application/json"

        private const val ASSISTANT = "saia:assistant:ImageAssistant"
        private const val REVISION = 1L
        private const val REVISION_NAME = "1"
    }

    /**
     * The filename should contain the name of the file with the extension
     */
    suspend fun uploadImage(image: ByteArray, filename: String, contentType: String): String {
        val apiKey = SecretsUtils.getGlobantApiKey()

        val shortFilename = filename.split(".")[0]

        val response: HttpResponse = client.post(FILE_UPLOAD_URL) {
            contentType(ContentType.MultiPart.FormData)
            headers {
                append(ACCEPT_HEADER, ACCEPT_HEADER_VALUE)
                append(HttpHeaders.Authorization, "Bearer $apiKey")
                append(ORGANIZATION_HEADER, ORGANIZATION)
                append(PROJECT_ID_HEADER, PROJECT_ID)
                // The filename param in the header is the image name without the extension for example instead of
                // image.png, it would be image
                append(FILE_NAME_HEADER, shortFilename)
            }

            setBody(MultiPartFormDataContent(
                formData {
                    append(
                        "file",
                        image,
                        Headers.build {
                            append(HttpHeaders.ContentType, contentType)
                            //
                            append(HttpHeaders.ContentDisposition, "filename=\"$filename\"")
                        }
                    )
                }
            ))
        }
        if (response.status == HttpStatusCode.OK) {
            return shortFilename
        }
        throw Exception("ImageUploadError: status: ${response.status}")
    }

    suspend fun sendMessagesToAI(messages: List<APIMessage>): APIResponse {

        val apiKey = SecretsUtils.getGlobantApiKey()

        val response: HttpResponse = client.post(ASSISTANT_CHAT_URL) {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $apiKey")
            }
            setBody(createAIRequest(messages))
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body<APIResponse>()
        }
        throw Exception("ImageAnalysisError: status: ${response.status}")
    }

    private fun createAIRequest(messages: List<APIMessage>) : APIRequest {
        return APIRequest(
            model = ASSISTANT,
            messages = messages,
            revision = REVISION,
            revisionName = REVISION_NAME,
        )
    }
}