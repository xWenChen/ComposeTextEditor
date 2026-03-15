package com.wellcherish.compose.texteditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.wellcherish.compose.texteditor.base.utils.FileEventBus
import com.wellcherish.compose.texteditor.ui.theme.ComposeTextEditorTheme
import com.wellcherish.compose.texteditor.ui.view.main.MainApp
import com.wellcherish.compose.texteditor.ui.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTextEditorTheme {
                MainApp()
            }
        }

        // 保持原有的初始化逻辑
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        FileEventBus.unregisterFileChangeListener(viewModel.onFileChanged)
    }

    private fun initData() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.changeLoadingState(true)
            withContext(Dispatchers.IO) {
                // FileSyncToDbManager.trySync()
                viewModel.init()
                FileEventBus.registerFileChangeListener(viewModel.onFileChanged)
            }
            viewModel.changeLoadingState(false)
        }
    }

    private fun startEditorPage() {
        //startActivity(Intent(this, EditorActivity::class.java))
    }
}