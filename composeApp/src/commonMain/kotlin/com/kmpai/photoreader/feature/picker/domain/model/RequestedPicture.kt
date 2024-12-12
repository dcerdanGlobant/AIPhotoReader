package com.kmpai.photoreader.feature.picker.domain.model

import androidx.compose.ui.graphics.ImageBitmap

/**
 * by default SharedImage creates a JPG image
 */
data class RequestedPicture(val byteArray: ByteArray, val bitmap: ImageBitmap, val extension: String = "jpg") {

    //using byteArray it is recommended to override those
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RequestedPicture

        if (!byteArray.contentEquals(other.byteArray)) return false
        if (bitmap != other.bitmap) return false
        if (extension != other.extension) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray.contentHashCode()
        result = 31 * result + bitmap.hashCode()
        result = 31 * result + extension.hashCode()
        return result
    }
}
