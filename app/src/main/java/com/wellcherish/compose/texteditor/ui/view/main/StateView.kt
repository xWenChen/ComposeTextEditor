package com.wellcherish.compose.texteditor.ui.view.main

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import com.wellcherish.compose.texteditor.base.dimenRes
import com.wellcherish.compose.texteditor.base.painterRes
import com.wellcherish.compose.texteditor.base.spDimenRes
import com.wellcherish.compose.texteditor.base.stringRes
import com.wellcherish.composetexteditor.base.R

@Composable
fun EmptyView() {
    StateView(
        R.drawable.ic_no_file.painterRes(),
        R.string.empty_page_tips.stringRes(),
    )
}

@Composable
fun LoadingView() {
    // 1. 定义无限循环的过渡动画
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")

    val rotation by infiniteTransition.animateFloat(
        // 1. 动画起始值：从 0 度开始
        initialValue = 0f,
        // 2. 动画目标值：转到 360 度结束
        targetValue = 360f,
        // 3. 动画的详细规格定义
        animationSpec = infiniteRepeatable(
            // 4. 定义具体的动画方式：
            // tween(1000) 表示动画时长为 1000 毫秒（1秒）
            // LinearEasing 表示匀速旋转（如果不加这个，默认会先慢后快再慢）
            animation = tween(1000, easing = LinearEasing), // 1秒转一圈
            // 5. 循环模式：Restart 表示从 360 度跳回 0 度重新开始
            // 如果想让它顺时针转完再逆时针转回，可以改用 RepeatMode.Reverse
            repeatMode = RepeatMode.Restart
        ),
        // 6. 标签：用于 Android Studio 的动画调试工具（Layout Inspector）, 方便定位
        label = "rotationAnimation"
    )

    StateView(
        R.drawable.ic_loading.painterRes(),
        R.string.data_syncing_tips.stringRes(),
        rotation
    )
}

@Composable
fun StateView(
    icon: Painter,
    text: String,
    // 动画的旋转角度
    rotateDegree: Float = 0f,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ImageView 对应 Image 组件
        Image(
            painter = icon,
            contentDescription = null, // 建议根据实际情况添加描述
            modifier = Modifier
                .size(R.dimen.empty_page_img_size.dimenRes())
                .padding(bottom = R.dimen.padding_middle.dimenRes())
                .rotate(rotateDegree),
            colorFilter = ColorFilter.tint(Color.Black)
        )

        // TextView 对应 Text 组件
        Text(
            text = text,
            color = Color.Black,
            fontSize = R.dimen.text_size_v2.spDimenRes()
        )
    }
}