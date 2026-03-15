package com.wellcherish.compose.texteditor.base.api.service

import com.wellcherish.compose.texteditor.base.bean.FileData
import com.wellcherish.compose.texteditor.base.bean.FileSystemFiles
import com.wellcherish.compose.texteditor.base.database.bean.FileItem

interface IFileDataService : IService {
    /**
     * 更新数据库。
     * */
    fun updateDbData(
        insertList: List<FileItem>?,
        deleteList: List<FileItem>?,
        updateList: List<FileItem>?,
    )

    /**
     * 从文件系统加载所有文件数据。
     * */
    fun loadFilesFromFileSystem(): FileSystemFiles

    /**
     * 从数据库查询记录。
     * */
    fun loadAllFiles(): List<FileItem>

    /**
     * 从数据库查询未删除的数据。
     * */
    fun loadNotDeletedFiles(): List<FileData>

    /**
     * 更新或者插入数据
     * */
    fun updateOrInsertDbData(fileItem: FileItem): Boolean
}