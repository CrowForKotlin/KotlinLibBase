package com.mordecai.base.tools.coroutine

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mordecai.base.tools.extensions.error
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/cn/barry/base/coroutine
 * @Time: 2022/5/5 9:02
 * @Author: CrowForKotlin
 * @Description: Coroutine Ext
 **************************/
val baseCoroutineException = GlobalCoroutineExceptionHandler()

class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {

    override val key: CoroutineContext.Key<*> get() = CoroutineExceptionHandler
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        "Catch GlobalCoroutineException : ${exception.stackTraceToString()}".error()
    }
}

inline fun LifecycleOwner.launchDelay(delay: Long, crossinline scope: suspend () -> Unit) {
    lifecycleScope.launch {
        delay(delay)
        scope()
    }
}

suspend fun <T> withUIContext(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Main, block)

suspend fun <T> withIOContext(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO, block)

suspend fun <T> withNonCancellableContext(block: suspend CoroutineScope.() -> T) =
    withContext(NonCancellable, block)

/**
 * 创建协程异常处理程序
 *
 * 2023-09-02 19:55:42 周六 下午
 */
fun createCoroutineExceptionHandler(content: String, handler: ((Throwable) -> Unit)? = null): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        handler?.invoke(throwable)
        "$content : ${throwable.stackTraceToString()}".error()
    }
}