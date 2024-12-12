package com.kmpai.photoreader.core.ui.views

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.loading
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource

@Composable
fun Loading() {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(vertical = 30.dp),
                text = stringResource(Res.string.loading),
                fontSize = 20.sp,
            )
            CircularProgressIndicator()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
