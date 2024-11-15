package com.mordecai.base.ui.viewmodel.viewstate

/**
 * ⦁  BaseViewState
 *
 * ⦁ 2024-11-06 22:12:45 周三 下午
 * @author crowforkotlin
 */
sealed class BaseViewState {

    // 用于预构建
    data object Default : BaseViewState()

    // 正在加载中
    data object Loading : BaseViewState()

    // 加载成功
    data object Success : BaseViewState()

    // 加载失败
    class Error(val code: Int = DEFAULT, val msg: String? = null) : BaseViewState() {
        companion object {
            const val DEFAULT = -1
            const val UNKNOW_HOST = -2
        }
    }
}