package com.wellcherish.compose.texteditor.base.utils

import com.wellcherish.compose.texteditor.base.constants.FileChangeType

typealias FileChangeListener = (String, FileChangeType) -> Unit

object FileEventBus {
    private val listenerMap = hashMapOf<FileChangeListener, Any?>()

    fun registerFileChangeListener(listener: FileChangeListener?) {
        listener ?: return
        listenerMap[listener] = null
    }

    fun unregisterFileChangeListener(listener: FileChangeListener?) {
        listener ?: return
        listenerMap.remove(listener)
    }

    fun notifyFileChanged(filePath: String?, type: FileChangeType?) {
        if (filePath.isNullOrBlank() || type == null) {
            // 无效文件，不通知
            return
        }
        listenerMap.forEach { (fileChanged, _) ->
            fileChanged(filePath, type)
        }
    }
}

