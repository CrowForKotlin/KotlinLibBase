package com.mordecai.base.ui.view.debounce

import com.mordecai.base.model.bean.BaseDebounceBean

// 点击事件接口
fun interface IBaseDebounce<T> { fun onSuccess(baseDebounceBean: BaseDebounceBean<T>) }
