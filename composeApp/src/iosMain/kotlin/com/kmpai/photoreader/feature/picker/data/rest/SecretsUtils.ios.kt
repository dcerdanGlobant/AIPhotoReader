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
            val bundle = NSBundle.bundleWithIdentifier( "com.kmpai.photoreader.AiPhotoReader.SharedImages")  // Asegúrate de usar el identificador correcto
            val fileURL = bundle?.URLForResource("secrets", "xml")

            println("Obteniendo")
            if (fileURL != null) {
                val fileData = NSData.dataWithContentsOfURL(fileURL)
                val fileContent = NSString.create(fileData!!, NSUTF8StringEncoding).toString()

                extractApiKeyFromXML(fileContent)
                    ?: throw Exception("No se encontró globant_api_key en el archivo XML")
            } else {
                throw Exception("Url nula")
            }
        } catch (e: Exception) {
            println("Error al obtener la API Key: ${e.cause}")
            throw Exception("globant_api_key resource not found")
        }
    }

    private fun extractApiKeyFromXML(xmlContent: String): String? {
        val regex = "<string\\s+name=\"globant_api_key\">(.*?)</string>".toRegex()
        val matchResult = regex.find(xmlContent)
        return matchResult?.groups?.get(1)?.value
    }
}
