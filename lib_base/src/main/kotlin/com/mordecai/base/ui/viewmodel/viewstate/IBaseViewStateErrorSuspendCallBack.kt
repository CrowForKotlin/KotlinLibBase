package com.mordecai.base.ui.viewmodel.viewstate

fun interface IBaseViewStateErrorSuspendCallBack { suspend fun callback(code: Int, msg: String?) }