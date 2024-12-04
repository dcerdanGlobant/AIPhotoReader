package com.kmpai.photoreader.feature.picker.ui.screens.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun ChatView(
    picture: ImageBitmap,
    isLoading: Boolean,
    description: String?,
    ) {

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                bitmap = picture,
                contentDescription = "Profile",
                modifier = Modifier.size(100.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            if(isLoading) {
                CircularProgressIndicator()
            } else {
                description?.let {
                    Text(it, Modifier.weight(1f))
                }
                /*Button(onClick = { openDialog.invoke() }, ){
                    Text("Select Another Image")
                }
                Button(onClick = { openChat.invoke() }, ){
                    Text("Get more info")
                }*/

            }
        }
    }
}