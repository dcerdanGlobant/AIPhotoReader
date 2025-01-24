package com.kmpai.photoreader.feature.picker.data.language

import java.util.Locale

actual fun getCurrentLanguageCode(): String = Locale.getDefault().language
