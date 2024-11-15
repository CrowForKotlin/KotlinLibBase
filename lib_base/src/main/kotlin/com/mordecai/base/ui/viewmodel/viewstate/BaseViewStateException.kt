package com.mordecai.base.ui.viewmodel.viewstate

//自定义error 可以抛出来结束流的运行
class BaseViewStateException(msg: String, throwable: Throwable? = null) : Exception(msg, throwable)