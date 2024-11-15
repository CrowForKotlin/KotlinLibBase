package com.mordecai.base.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordecai.base.tools.extensions.error
import com.mordecai.base.tools.extensions.setState
import com.mordecai.base.ui.viewmodel.viewstate.BaseViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/*************************
 * @ProjectName: JetpackApp
 * @Dir_Path: lib_base/src/main/java/cn/barry/base/viewmodel
 * @Time: 2022/4/26 9:37
 * @Author: CrowForKotlin
 * @Description: ViewModel 父类
 * @formatter:off
 **************************/
open class BaseViewModel : ViewModel(), IBaseViewModel {

    private var _mBaseViewState = MutableLiveData<BaseViewState>()
    val mBaseViewState: LiveData<BaseViewState> get() = _mBaseViewState

    /* 带有失败结果的流启动方式 */
    fun <T> flowLaunch(flow: Flow<T>, successEvent: IBaseViewModelEvent.OnSuccess<T>, failureEvent: IBaseViewModelEvent.OnFailure<Throwable>) = viewModelScope.launch {
        try {
            flow
                .onStart { _mBaseViewState.setState(BaseViewState.Loading) }
                .catch {
                    _mBaseViewState.setState(BaseViewState.Error(msg = it.localizedMessage))
                    failureEvent.onFailure(it)
                }
                .collect {
                    successEvent.onSuccess(it)
                    _mBaseViewState.setState(BaseViewState.Success)
                }
        } catch (e: Exception) {
            _mBaseViewState.setState(BaseViewState.Error(msg = e.localizedMessage))
            failureEvent.onFailure(e)
            "[flowLaunch] : ${e.stackTraceToString()}".error()
        }
    }

    /* 只有成功结果的流启动方式 */
    fun <T> flowLaunch(flow: Flow<T>, successEvent: IBaseViewModelEvent.OnSuccess<T>) = viewModelScope.launch {
        try {
            flow
                .onStart { _mBaseViewState.setState(BaseViewState.Loading) }
                .catch { _mBaseViewState.setState(BaseViewState.Error(msg = it.localizedMessage)) }
                .collect {
                    successEvent.onSuccess(it)
                    _mBaseViewState.setState(BaseViewState.Success)
                }
        } catch (e: Exception) {
            _mBaseViewState.setState(BaseViewState.Error(msg = e.localizedMessage))
            "[flowLaunch] : $e".error()
        }
    }
}