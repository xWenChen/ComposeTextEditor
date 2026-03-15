package com.wellcherish.compose.texteditor.ui.view.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wellcherish.compose.texteditor.base.bean.FileData

@Composable
fun FileList(
    fileList: List<FileData>, // 传入 state 列表
    onItemClick: (Int, FileData) -> Unit,
    onLongClick: (Int, FileData) -> Unit,
    onDeleteClick: (Int, FileData) -> Unit
) {
    // 瀑布流列表 (替代 StaggeredGridLayoutManager)
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(fileList, key = { _, item -> item.dbData?.id ?: 0L }) { index, data ->
            FileItem(
                data = data,
                index = index,
                onItemClick = onItemClick,
                onLongClick = onLongClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}