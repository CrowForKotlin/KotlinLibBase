package com.mordecai.base.ui.viewmodel.mvi

import com.mordecai.base.ui.viewmodel.viewstate.BaseViewState

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/kotlin/com/crow/base/viewmodel/mvi
 * @Time: 2023/3/9 19:18
 * @Author: CrowForKotlin
 * @Description: BaseMviEvent
 * @formatter:on
 **************************/
open class BaseMviIntent(var mViewState: BaseViewState = BaseViewState.Default, private var mState: Boolean = true) {
    fun withState(state: () -> Unit) {
        if (mState) {
            state()
        }
        mState = false
    }
}