package com.wellcherish.compose.texteditor.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wellcherish.compose.texteditor.MainApplication
import com.wellcherish.compose.texteditor.base.database.bean.FileItem
import com.wellcherish.compose.texteditor.base.database.constants.databaseName
import com.wellcherish.compose.texteditor.database.dao.FileItemDao

@Database(
    entities = [FileItem::class],
    version = 1,
    exportSchema = true // 此属性需要同时设置app的项目配置(详见app模块的build.gradle的javaCompileOptions配置)
)
abstract class FileItemDatabase : RoomDatabase() {

    abstract fun fileItemDao(): FileItemDao

    companion object {
        val db = Room.databaseBuilder(
            MainApplication.context,
            FileItemDatabase::class.java,
            databaseName
        ).fallbackToDestructiveMigration(false).build()
    }
}