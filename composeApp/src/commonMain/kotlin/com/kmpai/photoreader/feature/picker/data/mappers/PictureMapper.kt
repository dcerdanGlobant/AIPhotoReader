package com.kmpai.photoreader.feature.picker.data.mappers

import com.kmpai.photoreader.feature.picker.data.rest.model.ImageResponse
import com.kmpai.photoreader.feature.picker.domain.model.Picture

class PictureMapper {

    fun map(response: ImageResponse): Picture {
        return Picture(response.choices[0].message.content)
    }
}
