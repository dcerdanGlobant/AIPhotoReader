package com.kmpai.photoreader.feature.picker.ui.screens.home.views

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.loading
import aiphotoreader.composeapp.generated.resources.more_info
import aiphotoreader.composeapp.generated.resources.select_another_image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kmpai.photoreader.core.ui.views.ImageView
import com.kmpai.photoreader.core.ui.views.Loading
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PickerHomeView(
    picture: ImageBitmap,
    isLoading: Boolean,
    description: String?,
    openDialog: () -> Unit,
    openChat: () -> Unit
) {
    if (isLoading) Loading()
    else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f).padding(10.dp).verticalScroll(state = rememberScrollState())
            ) {
                ImageView(
                    modifier = Modifier.size(100.dp),
                    picture = picture,
                    description = description
                )
                Spacer(Modifier.weight(1f))
            }
            if (!isLoading) {
                FlowRow(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(onClick = { openDialog.invoke() }) {
                        Text(stringResource(Res.string.select_another_image))
                    }
                    Button(onClick = { openChat.invoke() }) {
                        Text(stringResource(Res.string.more_info))
                    }
                }
            }
        }
    }


}