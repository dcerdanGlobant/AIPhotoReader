package com.kmpai.photoreader.feature.gallery.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kmpai.photoreader.core.navigation.ScreenNavigatorConfig
import com.kmpai.photoreader.feature.gallery.ui.screen.GalleryScreen

val galleryNavConfig = ScreenNavigatorConfig(
    "gallery"
)

private val homeGalleryNavCongif = ScreenNavigatorConfig(
    "homeGallery"
)

fun NavGraphBuilder.addGalleryNavGraph(
    navController: NavController
) {
    navigation(startDestination = homeGalleryNavCongif.route, route = galleryNavConfig.route) {
        composable(route = homeGalleryNavCongif.route) {
            GalleryScreen()
        }
    }
}