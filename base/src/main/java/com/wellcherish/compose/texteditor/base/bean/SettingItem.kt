package com.wellcherish.compose.texteditor.base.bean

import android.view.View
import com.wellcherish.compose.texteditor.base.constants.SettingType

data class SettingItem(
    val type: SettingType,
    val text: String,
    val noDoubleClick: (View, Int, SettingItem) -> Unit,
)