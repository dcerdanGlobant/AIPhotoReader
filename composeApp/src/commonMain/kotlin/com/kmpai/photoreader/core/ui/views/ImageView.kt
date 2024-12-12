package com.kmpai.photoreader.core.ui.views

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.loading
import aiphotoreader.composeapp.generated.resources.no_data
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImageView(
    modifier: Modifier,
    picture: ImageBitmap,
    description: String?,
    ) {
    Column(modifier = Modifier.semantics(mergeDescendants = true) {},
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            bitmap = picture,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
        Text(description ?: stringResource(Res.string.no_data))
    }

}
