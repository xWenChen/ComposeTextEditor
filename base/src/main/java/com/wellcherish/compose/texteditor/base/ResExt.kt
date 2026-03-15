package com.wellcherish.compose.texteditor.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.wellcherish.compose.texteditor.MainApplication

@Composable
fun Int.stringRes(): String {
    return stringResource(this)
}

@Composable
fun Int.dimenRes(): Dp {
    return dimensionResource(this)
}

@Composable
fun Int.spDimenRes(): TextUnit {
    return dimensionResource(this).value.sp
}

@Composable
fun Int.colorRes(): Color {
    return colorResource(this)
}

@Composable
fun Int.painterRes(): Painter {
    return painterResource(this)
}