package com.kmpai.photoreader.feature.picker.domain.repository

import com.kmpai.photoreader.feature.picker.domain.model.Picture

interface PickerRepository {
    fun getPictureDescription(): Result<Picture>
}