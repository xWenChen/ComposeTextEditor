package com.wellcherish.compose.texteditor.ui.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wellcherish.compose.texteditor.base.bean.FileData
import com.wellcherish.compose.texteditor.base.res.AppColors
import com.wellcherish.compose.texteditor.base.utils.DrawableUtils
import com.wellcherish.compose.texteditor.base.utils.safeTitle
import com.wellcherish.composetexteditor.base.R

@Composable
fun FileItem(
    data: FileData,
    index: Int,
    onItemClick: (Int, FileData) -> Unit,
    onLongClick: (Int, FileData) -> Unit,
    onDeleteClick: (Int, FileData) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp) // 对应 xml 的 padding_middle
            .fillMaxWidth()
    ) {
        val gradientBg = remember { DrawableUtils.randomBg() }
        // 内容区域
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)) // 假设你需要圆角，原代码有 randomBg
                .background(gradientBg) // 需自行适配 Drawable 转 Color
                .clickable(
                    enabled = !data.showDelete, // 删除按钮展示时，不响应 item 的点击
                    onClick = { onItemClick(index, data) }
                )
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = { onLongClick(index, data) })
                }
                .padding(8.dp)
        ) {
            Text(
                text = data.dbData?.title.safeTitle().toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = data.text.toString(),
                fontSize = 20.sp,
                color = AppColors.White,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 删除按钮
        if (data.showDelete) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_full),
                contentDescription = "Delete",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .clickable { onDeleteClick(index, data) }
            )
        }
    }
}