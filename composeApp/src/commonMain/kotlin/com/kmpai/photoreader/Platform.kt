package com.kmpai.photoreader

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform