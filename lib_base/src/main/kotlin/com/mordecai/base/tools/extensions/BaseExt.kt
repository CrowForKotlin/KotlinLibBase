package com.mordecai.base.tools.extensions

import android.util.Log


fun String.removeWhiteSpace() = filterNot { it.isWhitespace() }

// 安全转换
fun<T : Any> safeAs(value: Any?): T? {
    return runCatching { value as T }.onFailure { "cast error! : ${it.stackTraceToString()}".error() }.getOrNull()
}

fun Any?.ifNull(block: () -> Unit) {
    if (this == null) block()
}

inline fun Any?.notNull(block: () -> Unit) {
    if (this != null) block()
}

fun String.isAllWhiteSpace(): Boolean {
    return Regex("\\s*").matches(this)
}