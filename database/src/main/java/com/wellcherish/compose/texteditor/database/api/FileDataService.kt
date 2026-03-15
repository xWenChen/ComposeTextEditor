package com.wellcherish.compose.texteditor.database.api

import com.google.auto.service.AutoService
import com.wellcherish.base.log.ZLog
import com.wellcherish.compose.texteditor.base.api.service.IFileDataService
import com.wellcherish.compose.texteditor.base.bean.FileData
import com.wellcherish.compose.texteditor.base.bean.FileSystemFiles
import com.wellcherish.compose.texteditor.base.database.bean.FileItem
import com.wellcherish.compose.texteditor.base.utils.fileContent
import com.wellcherish.compose.texteditor.base.utils.getDeletedFilesDir
import com.wellcherish.compose.texteditor.base.utils.getSaveDir
import com.wellcherish.compose.texteditor.database.FileItemDatabase

@AutoService(IFileDataService::class)
class FileDataService : IFileDataService {
    private val dao = FileItemDatabase.db.fileItemDao()

    override fun updateDbData(
        insertList: List<FileItem>?,
        deleteList: List<FileItem>?,
        updateList: List<FileItem>?
    ) {
        if (insertList != null) {
            dao.insertAll(insertList)
        }
        if (deleteList != null) {
            dao.deleteAll(deleteList)
        }
        if (updateList != null) {
            dao.updateAll(updateList)
        }
    }

    override fun loadFilesFromFileSystem(): FileSystemFiles {
        return try {
            FileSystemFiles(
                getSaveDir()?.listFiles()?.filterNotNull()?.associateBy { it.absolutePath },
                getDeletedFilesDir()?.listFiles()?.filterNotNull()?.associateBy { it.absolutePath }
            )
        } catch (e: Exception) {
            ZLog.e(TAG, e)
            FileSystemFiles()
        }
    }

    override fun loadAllFiles(): List<FileItem> {
        return try {
            // 查询出未被删除的数据。
            dao.queryAll() ?: emptyList()
        } catch (e: Exception) {
            ZLog.e(TAG, e)
            emptyList()
        }
    }

    override fun loadNotDeletedFiles(): List<FileData> {
        try {
            // 查询出未被删除的数据。
            val fileItems = dao.queryAllByTimeSort(false)
            if (fileItems.isNullOrEmpty()) {
                return emptyList()
            }
            return fileItems.map {
                FileData(
                    dbData = it,
                    text = it.filePath.fileContent()
                )
            }
        } catch (e: Exception) {
            ZLog.e(TAG, e)
            return emptyList()
        }
    }

    override fun updateOrInsertDbData(fileItem: FileItem): Boolean {
        runCatching {
            val oldItem = dao.queryByContentId(fileItem.contentId)
            if (oldItem == null) {
                // 新增
                dao.insertAll(fileItem)
                return true
            } else {
                // 更新
                return dao.updateAll(fileItem) > 0
            }
        }.onFailure {
            ZLog.e(TAG, it)
            return false
        }
        return false
    }

    companion object {
        private const val TAG = "FileDataService"
    }
}