package com.mordecai.base.ui.viewmodel.mvi

fun interface BaseMviSuspendResult<T> { suspend fun onResult(value: T) }