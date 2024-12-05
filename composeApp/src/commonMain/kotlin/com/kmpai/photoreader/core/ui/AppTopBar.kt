package com.kmpai.photoreader.core.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

data class TopBarConfig(
    val title: StringResource? = null,
    val backAllowed: Boolean = false,
)


@Composable
fun AppTopBar(
    config: TopBarConfig,
    onBackPressed: (() -> Unit)?,
) {
    TopAppBar(
        title = {
            Text(config.title?.let { stringResource(it) } ?: "")
        },
        navigationIcon = {
            if (config.backAllowed) {
                IconButton({ onBackPressed?.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                    )
                }
            }
        }
    )
}