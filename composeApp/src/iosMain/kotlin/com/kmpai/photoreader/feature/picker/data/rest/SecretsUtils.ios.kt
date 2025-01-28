package com.kmpai.photoreader.feature.picker.data.rest

import kotlinx.coroutines.runBlocking
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfURL

actual class ApiKeyLoader {
    actual suspend fun getApiKey(): String? {
        return try {
            val bundle = NSBundle.bundleWithIdentifier( "com.kmpai.photoreader.AiPhotoReader.SharedImages")
            val fileURL = bundle?.URLForResource("secrets", "xml")

            if (fileURL != null) {
                val fileData = NSData.dataWithContentsOfURL(fileURL)
                val fileContent = NSString.create(fileData!!, NSUTF8StringEncoding).toString()

                extractApiKeyFromXML(fileContent)
                    ?: throw Exception("Api key not found")
            } else {
                throw Exception("Null url")
            }
        } catch (e: Exception) {
            throw Exception("globant_api_key resource not found")
        }
    }

    private fun extractApiKeyFromXML(xmlContent: String): String? {
        val regex = "<string\\s+name=\"globant_api_key\">(.*?)</string>".toRegex()
        val matchResult = regex.find(xmlContent)
        return matchResult?.groups?.get(1)?.value
    }
}
