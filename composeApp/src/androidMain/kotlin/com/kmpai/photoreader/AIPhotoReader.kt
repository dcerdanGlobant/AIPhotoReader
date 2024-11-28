package com.kmpai.photoreader

import android.app.Application
import com.kmpai.photoreader.di.initKoin
import org.koin.android.ext.koin.androidContext

class AIPhotoReader: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AIPhotoReader)
        }
    }
}