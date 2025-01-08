package com.kmpai.photoreader.feature.permission

import androidx.compose.runtime.Composable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PermissionsManager(callback: PermissionCallback) {
    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()
}

interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus)
}

@Composable
expect fun createPermissionsManager(callback: PermissionCallback): PermissionsManager


enum class PermissionType {
    GALLERY,
    CAMERA
}

enum class PermissionStatus {
    GRANTED,
    DENIED,
    SHOW_RATIONALE
}