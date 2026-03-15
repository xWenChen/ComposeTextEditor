package com.wellcherish.compose.texteditor.data_api.datasource

import com.wellcherish.base.log.ZLog
import com.wellcherish.compose.texteditor.base.api.ServiceManager
import com.wellcherish.compose.texteditor.base.api.service.IFileDataService
import com.wellcherish.compose.texteditor.base.bean.FileData
import com.wellcherish.compose.texteditor.base.bean.FileSystemFiles
import com.wellcherish.compose.texteditor.base.database.bean.FileItem

object FileDataSource {
    private const val TAG = "FileDataSource"

    private val service = ServiceManager.getService(IFileDataService::class.java)

    /**
     * 更新数据库
     * */
    fun updateDbData(
        insertList: List<FileItem>?,
        deleteList: List<FileItem>?,
        updateList: List<FileItem>?,
    ) {
        service?.updateDbData(insertList, deleteList, updateList) ?: ZLog.w(TAG, "updateDbData, service null")
    }

    /**
     * 从文件系统加载所有文件数据。
     * */
    fun loadFilesFromFileSystem(): FileSystemFiles {
        return service?.loadFilesFromFileSystem() ?: run {
            ZLog.w(TAG, "loadFilesFromFileSystem, service null")
            FileSystemFiles()
        }
    }

    /**
     * 从数据库查询记录。
     * */
    fun loadAllFiles(): List<FileItem> {
        return service?.loadAllFiles() ?: run {
            ZLog.w(TAG, "loadAllFiles, service null")
            emptyList()
        }
    }

    /**
     * 从数据库查询未删除的数据。
     * */
    fun loadNotDeletedFiles(): List<FileData> {
        return service?.loadNotDeletedFiles() ?: run {
            ZLog.w(TAG, "loadNotDeletedFiles, service null")
            emptyList()
        }
    }

    /**
     * 更新或者插入数据
     * */
    fun updateOrInsertDbData(fileItem: FileItem): Boolean {
        return service?.updateOrInsertDbData(fileItem) ?: run {
            ZLog.w(TAG, "loadNotDeletedFiles, service null")
            false
        }
    }
}