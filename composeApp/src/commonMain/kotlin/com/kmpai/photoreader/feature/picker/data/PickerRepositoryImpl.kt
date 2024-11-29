package com.kmpai.photoreader.feature.picker.data

import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class PickerRepositoryImpl: PickerRepository {
    override fun getPictureDescription(): Result<Picture> {
        return Result.success(Picture("",
            ""))
    }
}