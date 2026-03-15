package com.wellcherish.compose.texteditor.ui.view.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wellcherish.compose.texteditor.base.bean.FileData
import com.wellcherish.compose.texteditor.ui.classic.EditorAppBar
import com.wellcherish.compose.texteditor.ui.classic.LocalNavController
import com.wellcherish.compose.texteditor.ui.constants.Route
import com.wellcherish.compose.texteditor.ui.viewmodel.MainViewModel
import com.wellcherish.compose.texteditor.utils.DataManager

@Composable
fun MainApp() {
    // 1. 在这里声明“真实”的 navController
    // 它是整个 App 路由状态的唯一持有者
    val navController = rememberNavController()

    // 2、将实例包裹在 Provider 中，注入给local对象，后面可以随时获取。
    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {
        NavHost(navController = navController, startDestination = Route.MAIN_PAGE) {
            composable(Route.MAIN_PAGE) {
                MainPage() // 以后这里再也不用传参数了！
            }
            /*composable("editor") {
                EditorPage(navController)
            }*/
        }
    }
}

@Composable
fun MainPage() {
    val viewModel = viewModel<MainViewModel>()
    // 1. 观察所有状态
    val showLoading by viewModel.showLoading.observeAsState(false)
    val showEmpty by viewModel.showEmpty.observeAsState(false)
    val dataState by viewModel.dataListLiveData.observeAsState()
    // val isDeleting by remember { mutableStateOf(viewModel.isDeleting) }
    val isDeleting by viewModel.isDeleting.observeAsState(false)

    // 2. 处理返回键拦截 (替代 BackPressedCallback)
    // 【优化】enabled 应该动态绑定。只有在删除态时才拦截。
    BackHandler(enabled = isDeleting) {
        // 如果处于删除态，点击返回键则取消删除态
        viewModel.changeDeletingState(false)
    }

    // 3. 页面根布局
    Scaffold(
        topBar = {
            EditorAppBar(showSaveIcon = false)
        },
        content = { padding ->
            // 4. 状态切换逻辑 (替代 checkPageState)
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                when {
                    showLoading -> LoadingView()
                    showEmpty || dataState?.dataList.isNullOrEmpty() -> EmptyView()
                    else -> {
                        FileContent(
                            list = dataState?.dataList ?: emptyList(),
                            onItemClick = { index, data ->
                                DataManager.chosenFileData = data
                                // onStartEditor()
                            },
                            onItemLongClick = { index, data ->viewModel.changeDeletingState(true) },
                            onDelete = { index, data -> viewModel.deleteItem(index, data) }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun FileContent(
    list: List<FileData>,
    onItemClick: (Int, FileData) -> Unit,
    onItemLongClick: (Int, FileData) -> Unit,
    onDelete: (Int, FileData) -> Unit
) {
    Column {
        // 文件数量提示
        Text(
            text = "共 ${list.size} 个文件",
            modifier = Modifier.padding(16.dp)
        )

        FileList(
            fileList = list,
            onItemClick = onItemClick,
            onLongClick = onItemLongClick,
            onDeleteClick = onDelete
        )
    }
}