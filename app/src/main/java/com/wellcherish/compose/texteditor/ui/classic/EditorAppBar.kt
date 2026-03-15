package com.wellcherish.compose.texteditor.ui.classic

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.wellcherish.compose.texteditor.base.colorRes
import com.wellcherish.compose.texteditor.base.dimenRes
import com.wellcherish.compose.texteditor.base.painterRes
import com.wellcherish.compose.texteditor.base.stringRes
import com.wellcherish.composetexteditor.base.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorAppBar(
    title: @Composable () -> Unit = {
        Text(
            R.string.app_name.stringRes(),
            color = Color.White,
        )
    },
    navigationRes: @Composable () -> Painter = { R.drawable.ic_back.painterRes() },
    showSaveIcon: Boolean = true,
    showSettingIcon: Boolean = true,
    onSaveClick: () -> Unit = {},
) {
    val navController = LocalNavController.current
    val activity = LocalActivity.current

    val iconSize = R.dimen.icon_size_middle.dimenRes()
    val backgroundColor = R.color.rb_green.colorRes()
    val contentColor = R.color.white.colorRes()

    TopAppBar(
        // 设置容器颜色（背景）和内容颜色（图标）
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            navigationIconContentColor = contentColor,
            titleContentColor = contentColor,
            actionIconContentColor = contentColor
        ),
        navigationIcon = {
            FilledIconButton(
                onClick = {
                    backToLastPageOrExitApp(navController, activity)
                },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = backgroundColor,
                    contentColor = contentColor,
                ),
                content = {
                    Image(
                        navigationRes(),
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "返回",
                    )
                }
            )
        },
        title = title,
        actions = {
            if (showSaveIcon) {
                FilledIconButton(
                    onClick = onSaveClick,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = backgroundColor,
                        contentColor = contentColor,
                    ),
                    content = {
                        Image(
                            R.drawable.ic_save.painterRes(),
                            modifier = Modifier.size(iconSize),
                            contentScale = ContentScale.Crop,
                            contentDescription = "保存"
                        )
                    }
                )
            }
            if (showSettingIcon) {
                FilledIconButton(
                    onClick = { jumpToSettingPage(activity) },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = backgroundColor,
                        contentColor = contentColor,
                    ),
                    content = {
                        Image(
                            R.drawable.ic_setting.painterRes(),
                            modifier = Modifier.size(iconSize),
                            contentScale = ContentScale.Crop,
                            contentDescription = "设置"
                        )
                    }
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),

    )
}

private fun jumpToSettingPage(activity: Activity?) {
    activity ?: return

}

/**
 * 返回上一页，如果在首页，则退出应用。
 * */
private fun backToLastPageOrExitApp(navController: NavHostController, activity: Activity?) {
    // 尝试出栈，如果失败（返回false），说明已经到头了
    if (!navController.popBackStack()) {
        // 这里的逻辑等同于 onBackPressed()，直接关闭 Activity
        activity?.finish()
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun EditorAppBarPreview(){
    MaterialTheme {
        EditorAppBar()
    }
}