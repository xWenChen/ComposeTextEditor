package com.wellcherish.compose.texteditor.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wellcherish.base.log.ZLog
import com.wellcherish.compose.texteditor.base.bean.FileData
import com.wellcherish.compose.texteditor.base.bean.FileDataResult
import com.wellcherish.compose.texteditor.base.constants.FileChangeType
import com.wellcherish.compose.texteditor.data_api.datasource.FileDataSource
import com.wellcherish.compose.texteditor.utils.DeleteFileUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val showLoading = MutableLiveData(false)
    val showEmpty = MutableLiveData(false)
    /**
     * 是否是长按删除中的状态，如果是，则每个item展示删除按钮。
     * */
    var isDeleting = MutableLiveData(false)
    val dataListLiveData = MutableLiveData<FileDataResult>()

    val onFileChanged = run@{ _: String, changeType: FileChangeType ->
        ZLog.d(TAG, "main vm onFileChanged, changeType=$changeType")
        if (changeType == FileChangeType.UNKNOWN) {
            return@run
        }
        viewModelScope.launch(Dispatchers.IO) {
            ZLog.d(TAG, "main vm onFileChanged, loadData, changeType=$changeType")
            // 重新加载数据
            loadData()
        }
    }

    fun changeDeletingState(isDeleting: Boolean) {
        if (this.isDeleting.value == isDeleting) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            this@MainViewModel.isDeleting.postValue(isDeleting)
            val list = dataListLiveData.value?.dataList?.map { it.copy(showDelete = isDeleting) }
            dataListLiveData.postValue(FileDataResult(list, false))
        }
    }

    fun deleteItem(position: Int, data: FileData) {
        val list = dataListLiveData.value?.dataList
        if (list.isNullOrEmpty()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            // 文件引入应用回收站目录。数据库数据标记为删除态。
            if (!DeleteFileUtil.recycleFile(data)) {
                return@launch
            }
            val newList = mutableListOf<FileData>()
            for (item in list) {
                if (item.dbData?.contentId == data.dbData?.contentId) {
                    // 是被删除的数据，则不加入新列表。
                    continue
                }
                newList.add(item.copy())
            }
            dataListLiveData.postValue(FileDataResult(newList, false))
        }
    }

    fun changeLoadingState(isShow: Boolean) {
        if (showLoading.value == isShow) {
            return
        }
        showLoading.postValue(isShow)
    }

    suspend fun init() {
        loadData()
    }

    private suspend fun loadData() {
        val files = withContext(Dispatchers.IO) {
            runCatching {
                FileDataSource.loadNotDeletedFiles()
            }.onFailure {
                ZLog.e(TAG, it)
            }.getOrNull()
        }
        dataListLiveData.postValue(FileDataResult(files, true))
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}