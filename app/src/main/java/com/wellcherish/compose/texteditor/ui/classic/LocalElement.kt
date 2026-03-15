package com.wellcherish.compose.texteditor.ui.classic

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

// 1、定义 LocalProvider
val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided")
}
