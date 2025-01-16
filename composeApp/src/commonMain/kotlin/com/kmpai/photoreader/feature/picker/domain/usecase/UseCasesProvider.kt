package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.di.KoinDependency
import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.experimental.ExperimentalObjCName
import kotlin.jvm.JvmStatic
import kotlin.native.ObjCName


@OptIn(ExperimentalObjCName::class)
@ObjCName("GetPictureDescriptionWrapper", swiftName = "GetPictureDescriptionWrapper")
class GetPictureDescriptionWrapper {

    companion object {

        @JvmStatic
        @ObjCName("getPictureDescriptionDirect", swiftName = "getPictureDescription")
        fun getPictureDescriptionDirect(
            image: ByteArray,
            extension: String,
            callback: (CommonResult<Conversation>) -> Unit
        ) {
            val repository = KoinDependency.koinApplication.koin.get<PickerRepository>()
            val getPictureDescription = GetPictureDescription(repository)

            MainScope().launch {
                try {
                    getPictureDescription.invoke(image, extension).collect { result ->
                        callback(result)
                    }
                } catch (e: Exception) {
                    callback(CommonResult.Failure(e))
                }
            }
        }
    }
}
