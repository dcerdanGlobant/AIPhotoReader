package com.kmpai.photoreader.feature.picker.ui.screens.home

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.camera_permission_message
import aiphotoreader.composeapp.generated.resources.cancel
import aiphotoreader.composeapp.generated.resources.permission_required
import aiphotoreader.composeapp.generated.resources.settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.kmpai.photoreader.feature.picker.ui.rememberSharedManager
import com.kmpai.photoreader.feature.picker.ui.screens.home.views.PickView
import com.kmpai.photoreader.feature.picker.ui.screens.home.views.PickerHomeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PickerHomeScreen (
    viewModel: PickerViewModel = koinViewModel<PickerViewModel>(),
    onOpenChat: () -> Unit,
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

        PickerHomeState.SharedPicture -> {
            rememberSharedManager { image: SharedImage? ->
                useImage(coroutineScope, image, viewModel)
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
            PickView() { viewModel.showImageSourceOptionDialog()}
        }
        is PickerHomeState.PickedPicture -> {
            val pickedState = homeState as PickerHomeState.PickedPicture
            PickerHomeView(
                picture = pickedState.picture,
                isLoading = pickedState.isLoading,
                description = pickedState.description,
                openDialog = { viewModel.showImageSourceOptionDialog() },
                openChat = {onOpenChat.invoke() }

            )
        }

        PickerHomeState.ShowRationaleDialog -> {
            cameraPermissionAlertDialog(viewModel)

        }
    }
}

@Composable
private fun cameraPermissionAlertDialog(viewModel: PickerViewModel) {
    AlertMessageDialog(title = stringResource(Res.string.permission_required),
        message = stringResource(Res.string.camera_permission_message),
        positiveButtonText = stringResource(Res.string.settings),
        negativeButtonText = stringResource(Res.string.cancel),
        onPositiveClick = {
            viewModel.launchSettings()

        },
        onNegativeClick = {
            viewModel.showPickImage()
        })
}

private fun useImage(
    coroutineScope: CoroutineScope,
    image: SharedImage?,
    viewModel: PickerViewModel
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

