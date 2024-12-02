package com.kmpai.photoreader.feature.picker.data.datasource

import android.content.Context
import com.kmpai.photoreader.feature.picker.domain.datasource.LocalImageDataSource
import java.io.ByteArrayOutputStream

actual class LocalImageDatasourceImpl(private val context: Context) : LocalImageDataSource {

    override suspend fun getImageByteArray(filename: String): ByteArray? {
        //TODO: load image

        //TEST CODE... TODO: REMOVE
        return try {
            val inputStream = context.assets.open("little_cat.jpeg")
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, len)
            }
            byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}