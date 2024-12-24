package com.kmpai.photoreader.feature.picker.data.rest.mappers

import com.kmpai.photoreader.feature.picker.data.rest.model.ImageResponse
import com.kmpai.photoreader.feature.picker.domain.model.Picture


fun ImageResponse.toPictureModel() =
    Picture(this.choices[0].message.content)
