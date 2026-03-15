package com.wellcherish.compose.texteditor.base.bean

import com.wellcherish.compose.texteditor.base.database.bean.FileItem

data class FileData(
    var dbData: FileItem? = null,
    var text: CharSequence? = null,
    var showDelete: Boolean = false
)