package com.kmpai.photoreader.feature.picker.data.language

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun getCurrentLanguageCode() = NSLocale.currentLocale().languageCode
