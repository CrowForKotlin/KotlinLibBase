package com.mordecai.base.model.bean

import com.mordecai.base.ui.view.debounce.BaseDebounce

data class BaseDebounceBean<T>(
    val mType: T,
    val mBaseDebounce: BaseDebounce,
)
