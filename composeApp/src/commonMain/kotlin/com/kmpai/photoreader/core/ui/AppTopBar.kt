package com.kmpai.photoreader.core.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class TopBarConfig(
    val title: StringResource? = null,
    val backAllowed: Boolean = false,
)


@OptIn(ExperimentalMaterial3Api::class)
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

@Composable
@Preview
fun AppTopBarPreview() {
    AppTopBar(
        config = TopBarConfig(
            title = null,
            backAllowed = true
        ), {})

}