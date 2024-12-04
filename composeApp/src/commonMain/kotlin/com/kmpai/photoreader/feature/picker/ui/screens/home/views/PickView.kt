package com.kmpai.photoreader.feature.picker.ui.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun PickView(
    openDialog: () -> Unit
    ) {

    Box(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { openDialog.invoke() }, Modifier.align(Alignment.BottomCenter)){
            Text("Select Image")
        }
    }
}