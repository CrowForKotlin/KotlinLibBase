package com.mordecai.base.ui.viewmodel

sealed interface IBaseViewModelEvent {
    fun interface OnSuccess<T> {
        fun onSuccess(value: T)
    }

    fun interface OnFailure<T> {
        fun onFailure(value: T)
    }
}