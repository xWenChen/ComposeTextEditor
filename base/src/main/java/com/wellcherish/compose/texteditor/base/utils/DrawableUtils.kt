package com.wellcherish.compose.texteditor.base.utils

import android.graphics.drawable.GradientDrawable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.wellcherish.compose.texteditor.base.res.AppColors
import java.util.*

object DrawableUtils {
    private const val TAG = "DrawableUtils"

    private val colorList = mutableListOf<Color>().apply {
        add(AppColors.RainbowBlue)
        add(AppColors.DeepPink)
        add(AppColors.DeepGreen)
        add(AppColors.LightPurple)
        add(AppColors.LightOrange)
    }

    private val random = Random()

    fun randomBg(
        orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TL_BR,
    ): Brush {
        val size = colorList.size
        // 取 0 到 bgList.size - 1 的随机数。
        val startIndex = random.nextInt(size)
        var endIndex = random.nextInt(size)
        if (startIndex == endIndex) {
            endIndex = (startIndex + 1) % size
        }
        // 方向：左上到右下
        return Brush.linearGradient(
            colors = listOf(colorList[startIndex], colorList[endIndex]),
            start = Offset.Zero, // 左上角
            end = Offset.Infinite // 自动扩展到右下角
        )
    }
}