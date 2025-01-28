package com.kmpai.photoreader.feature.picker.data.language

class LanguageHelperImpl: LanguageHelper {

    override fun getLanguageCode(): String {
        return getCurrentLanguageCode().ifEmpty { return "en" }
    }
}
