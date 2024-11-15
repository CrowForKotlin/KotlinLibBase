package com.mordecai.base.tools.extensions

import com.mordecai.base.ui.viewmodel.viewstate.BaseViewState
import com.mordecai.base.ui.viewmodel.viewstate.IBaseViewStateCallBack
import com.mordecai.base.ui.viewmodel.viewstate.IBaseViewStateErrorCallBack
import com.mordecai.base.ui.viewmodel.viewstate.IBaseViewStateErrorSuspendCallBack
import com.mordecai.base.ui.viewmodel.viewstate.IBaseViewStateSuspendCallBack

suspend inline fun BaseViewState.doOnLoadingInCoroutine(crossinline block: suspend () -> Unit): BaseViewState {
    if (this is BaseViewState.Loading) block()
    return this
}

suspend inline fun BaseViewState.doOnSuccessInCoroutine(crossinline block: suspend () -> Unit): BaseViewState {
    if (this is BaseViewState.Success) block()
    return this
}

suspend inline fun BaseViewState.doOnErrorInCoroutine(crossinline block: suspend (Int, String?) -> Unit): BaseViewState {
    if (this is BaseViewState.Error) block(code, msg)
    return this
}

inline fun BaseViewState.doOnLoadingInline(crossinline block: () -> Unit): BaseViewState {
    if (this is BaseViewState.Loading) block()
    return this
}

inline fun BaseViewState.doOnSuccessInline(crossinline block: () -> Unit): BaseViewState {
    if (this is BaseViewState.Success) block()
    return this
}

inline fun BaseViewState.doOnErrorInline(crossinline block: (Int, String?) -> Unit): BaseViewState {
    if (this is BaseViewState.Error) block(code, msg)
    return this
}

fun BaseViewState.doOnLoading(iViewStateCallBack: IBaseViewStateCallBack): BaseViewState {
    if (this is BaseViewState.Loading) iViewStateCallBack.callback()
    return this
}

fun BaseViewState.doOnSuccess(iViewStateCallBack: IBaseViewStateCallBack): BaseViewState {
    if (this is BaseViewState.Success) iViewStateCallBack.callback()
    return this
}

fun BaseViewState.doOnError(iViewStateErrorCallBack: IBaseViewStateErrorCallBack): BaseViewState {
    if (this is BaseViewState.Error) iViewStateErrorCallBack.callback(code, msg)
    return this
}


suspend fun BaseViewState.doOnLoadingSuspend(iViewStateCallBack: IBaseViewStateSuspendCallBack):
        BaseViewState {
    if (this is BaseViewState.Loading) iViewStateCallBack.callback()
    return this
}

suspend fun BaseViewState.doOnSuccessSuspend(iViewStateCallBack: IBaseViewStateSuspendCallBack):
        BaseViewState {
    if (this is BaseViewState.Success) iViewStateCallBack.callback()
    return this
}

suspend fun BaseViewState.doOnErrorSuspend(iViewStateErrorCallBack: IBaseViewStateErrorSuspendCallBack): BaseViewState {
    if (this is BaseViewState.Error) iViewStateErrorCallBack.callback(code, msg)
    return this
}