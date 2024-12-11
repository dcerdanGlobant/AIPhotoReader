package com.kmpai.photoreader.feature.picker.ui.screens.home.views

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.select_image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource


@Composable
fun PickView(
    openDialog: () -> Unit
    ) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(onClick = { openDialog.invoke() }, Modifier.align(Alignment.BottomCenter)){
            Text(stringResource(Res.string.select_image))
        }
    }
}