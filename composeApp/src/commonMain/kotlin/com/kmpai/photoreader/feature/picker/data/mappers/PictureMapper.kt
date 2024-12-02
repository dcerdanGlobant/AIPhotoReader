package com.kmpai.photoreader.feature.picker.data.mappers

import com.kmpai.photoreader.feature.picker.data.rest.model.ImageResponse
import com.kmpai.photoreader.feature.picker.domain.model.Picture

class PictureMapper {

    fun map(imagePath: String, response: ImageResponse): Picture {
        return Picture(imagePath, response.choices[0].message.content)
    }
}
