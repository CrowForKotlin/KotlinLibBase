package com.mordecai.base.ui.viewmodel.mvi

fun interface BaseMviFlowResult<R : BaseMviIntent, T> { suspend fun onResult(value: T): R }
