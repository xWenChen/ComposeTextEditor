package com.wellcherish.compose.texteditor.base.api

import com.wellcherish.base.log.ZLog
import com.wellcherish.compose.texteditor.base.api.service.IService
import java.util.ServiceLoader

object ServiceManager {
    const val TAG = "ServiceManager"

    fun <T : IService> getService(serviceClass: Class<T>): T? {
        return try {
            // 自动寻找所有标记了 @AutoService(IService::class) 的实现类
            val loader = ServiceLoader.load(serviceClass)
            return loader.firstOrNull()
        } catch (e: Exception) {
            ZLog.e(TAG, e)
            null
        }
    }


}