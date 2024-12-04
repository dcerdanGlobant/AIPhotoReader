package com.kmpai.photoreader.feature.picker.ui.screens.home

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.chat_ia
import aiphotoreader.composeapp.generated.resources.image_description
import aiphotoreader.composeapp.generated.resources.select_image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.kmpai.photoreader.core.ui.TopBarConfig
import com.kmpai.photoreader.feature.permission.AlertMessageDialog
import com.kmpai.photoreader.feature.permission.ImageSourceOptionDialog
import com.kmpai.photoreader.feature.permission.PermissionCallback
import com.kmpai.photoreader.feature.permission.PermissionStatus
import com.kmpai.photoreader.feature.permission.PermissionType
import com.kmpai.photoreader.feature.permission.createPermissionsManager
import com.kmpai.photoreader.feature.picker.domain.model.RequestedPicture
import com.kmpai.photoreader.feature.picker.ui.SharedImage
import com.kmpai.photoreader.feature.picker.ui.rememberCameraManager
import com.kmpai.photoreader.feature.picker.ui.rememberGalleryManager
import com.kmpai.photoreader.feature.picker.ui.screens.home.views.ChatView
import com.kmpai.photoreader.feature.picker.ui.screens.home.views.PickView
import com.kmpai.photoreader.feature.picker.ui.screens.home.views.PickerHomeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PickerHomeScreen (
    viewModel: PickerHomeViewModel = koinViewModel<PickerHomeViewModel>(),
    onTopBarConfig: ((topBarConfig: TopBarConfig) -> Unit),
) {
    val homeState by viewModel.homeState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.GALLERY -> viewModel.launchGallery()
                        PermissionType.CAMERA -> viewModel.launchCamera()
                    }
                }

                else -> {
                    viewModel.showRationaleDialog()
                }
            }
        }
    })

    val galleryManager = rememberGalleryManager { image: SharedImage? ->
        useImage(coroutineScope, image, viewModel)
    }

    val cameraManager = rememberCameraManager { image: SharedImage? ->
        useImage(coroutineScope, image, viewModel)
    }

    when (homeState) {
        PickerHomeState.ImageSourceOptionDialog -> {
            onTopBarConfig.invoke(TopBarConfig(Res.string.select_image, false))
            ImageSourceOptionDialog(onDismissRequest = {
                viewModel.showPickImage()
            }, onGalleryRequest = {
                viewModel.launchGallery()
            }, onCameraRequest = {
                viewModel.launchCamera()
            })
        }

        PickerHomeState.LaunchCamera -> {
            if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
                SideEffect { cameraManager.launch() }
            } else {
                permissionsManager.askPermission(PermissionType.CAMERA)
            }
        }

        PickerHomeState.LaunchGalery -> {
            if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
                SideEffect { galleryManager.launch() }
            } else {
                permissionsManager.askPermission(PermissionType.GALLERY)
            }
        }

        PickerHomeState.LaunchSettings -> permissionsManager.launchSettings()
        PickerHomeState.PickPicture ->  {
            onTopBarConfig.invoke(TopBarConfig(Res.string.select_image, false))
            PickView() { viewModel.showImageSourceOptionDialog()}
        }
        is PickerHomeState.PickedPicture -> {
            onTopBarConfig.invoke(TopBarConfig(Res.string.image_description, false))
            val pickedState = homeState as PickerHomeState.PickedPicture
            PickerHomeView(
                picture = pickedState.picture,
                isLoading = pickedState.isLoading,
                description = pickedState.description,
                openDialog = { viewModel.showImageSourceOptionDialog() },
                openChat = { viewModel.openChat() }

            )
        }

        PickerHomeState.ShowRationaleDialog -> {
            AlertMessageDialog(title = "Permission Required",
                message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
                positiveButtonText = "Settings",
                negativeButtonText = "Cancel",
                onPositiveClick = {
                    viewModel.launchSettings()

                },
                onNegativeClick = {
                    viewModel.showPickImage()
                })

        }

        is PickerHomeState.OpenChat ->  {
            onTopBarConfig.invoke(TopBarConfig(Res.string.chat_ia, true))
            val pickedState = homeState as PickerHomeState.OpenChat
            ChatView(
                picture = pickedState.picture,
                description = pickedState.description,
                isLoading = false
            )
        }
    }
}

private fun useImage(
    coroutineScope: CoroutineScope,
    image: SharedImage?,
    viewModel: PickerHomeViewModel
) {
    coroutineScope.launch {
        withContext(Dispatchers.Default) {
            val byteArray = image?.toByteArray()
            val bitmap = image?.toImageBitmap()
            if (byteArray != null && bitmap != null)
                RequestedPicture(byteArray, bitmap)
            else null
        }?.let {
            viewModel.getPictureData(it)
        } ?: viewModel.showPickImage()
    }
}

