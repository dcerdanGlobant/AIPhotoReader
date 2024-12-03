package com.kmpai.photoreader.feature.gallery.ui.screen

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.compose_multiplatform
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.kmpai.photoreader.feature.gallery.ui.rememberCameraManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import com.kmpai.photoreader.feature.gallery.ui.rememberGalleryManager
import com.kmpai.photoreader.feature.permission.AlertMessageDialog
import com.kmpai.photoreader.feature.permission.ImageSourceOptionDialog
import com.kmpai.photoreader.feature.permission.PermissionCallback
import com.kmpai.photoreader.feature.permission.PermissionStatus
import com.kmpai.photoreader.feature.permission.PermissionType
import com.kmpai.photoreader.feature.permission.createPermissionsManager

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryScreen() {
    val coroutineScope = rememberCoroutineScope()
    var imageBitmap by remember { mutableStateOf< ImageBitmap?>(null)}
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false)}
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.GALLERY -> launchGallery = true
                        PermissionType.CAMERA -> launchCamera = true
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }
    })


    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            imageBitmap = bitmap
        }
    }

    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            imageBitmap = bitmap
        }
    }

    if (imageSourceOptionDialog) {
        ImageSourceOptionDialog(onDismissRequest = {
            Logger.d("on touch gallery dismiss")
            imageSourceOptionDialog = false
        }, onGalleryRequest = {
            Logger.d("on touch gallery request")
            imageSourceOptionDialog = false
            launchGallery = true
        }, onCameraRequest = {
            imageSourceOptionDialog = false
            launchCamera = true
        })
    }

    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            SideEffect {
                galleryManager.launch()
            }

        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery = false
    }

    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        launchCamera = false
    }

    if (launchSetting) {

        permissionsManager.launchSettings()
        launchSetting = false
    }

    if (permissionRationalDialog) {
        AlertMessageDialog(title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onPositiveClick = {
                permissionRationalDialog = false
                launchSetting = true

            },
            onNegativeClick = {
                permissionRationalDialog = false
            })

    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            Logger.d("open dialog")
            imageSourceOptionDialog = true
        }) {
            Text("open dialog", Modifier.combinedClickable(
                onClick = {
                    Logger.d("open dialog")
                    imageSourceOptionDialog = true
                }
            ))
        }
        if (imageBitmap != null) {
            Text("Image not null")
            Image(
                bitmap = imageBitmap!!,
                contentDescription = "Profile",
                modifier = Modifier.size(100.dp).clip(CircleShape).clickable {
                    imageSourceOptionDialog = true
                },
                contentScale = ContentScale.Crop
            )
        } /*else {
            Text("Image loaded")
            imageSourceOptionDialog = true
            /*Image(
                modifier = Modifier.size(100.dp).clip(CircleShape).clickable {
                    imageSourceOptionDialog = true
                },
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "Profile",
            )*/
        }*/
    }

}