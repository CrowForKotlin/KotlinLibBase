@file:Suppress("unused")

package com.mordecai.base.tools.extensions

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.mordecai.base.ui.view.debounce.BaseDebounce
import com.mordecai.base.ui.view.debounce.BaseDebounce.Companion.BASE_FLAG_TIME_400
import com.mordecai.base.ui.view.debounce.IBaseDebounce
import com.mordecai.base.ui.view.debounce.IBaseDebounceExt
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.materialswitch.MaterialSwitch

/************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/com/barry/base/extensions
 * @Time: 2022/7/10 2:23
 * @Author: CrowForKotlin
 * @Description: Event Extension
 * @formatter:off
 **************************/


// View 点击事件间隔 默认1秒
fun View.setClickListener(isGlobal:Boolean = true, flagTime: Long = BASE_FLAG_TIME_400, msg: String? = null, iEven: IBaseDebounce<View>) {
    val baseDebounce = if (isGlobal) BaseDebounce.getSIngleInstance() else BaseDebounce.newInstance(flagTime)
    setOnClickListener { iEven.onSuccess(baseDebounce.getIntervalResult(this, msg, baseDebounce) ?: return@setOnClickListener) }
}

// View 扩展 onFailure
fun View.setClickListener(isGlobal:Boolean = true, flagTime: Long = BASE_FLAG_TIME_400, iEven: IBaseDebounceExt<View>) {
    val baseDebounce = if (isGlobal) BaseDebounce.getSIngleInstance() else BaseDebounce.newInstance(flagTime)
    setOnClickListener { baseDebounce.interval(this, baseDebounce, iEven) }
}

// MenuItem 点击事件间隔 默认1秒
fun MenuItem.setClickListener(isGlobal:Boolean = true, flagTime: Long = BASE_FLAG_TIME_400, msg: String? = null, iEven: IBaseDebounce<MenuItem>) {
    val baseDebounce = if (isGlobal) BaseDebounce.getSIngleInstance() else BaseDebounce.newInstance(flagTime)
    setOnMenuItemClickListener {
        iEven.onSuccess(baseDebounce.getIntervalResult(this, msg, baseDebounce) ?: return@setOnMenuItemClickListener true)
        true
    }
}

// MenuItem 扩展 onFailure
fun MenuItem.setClickListener(isGlobal:Boolean = true, flagTime: Long = BASE_FLAG_TIME_400, iEvent: IBaseDebounceExt<MenuItem>) {
    val baseDebounce = if (isGlobal) BaseDebounce.getSIngleInstance() else BaseDebounce.newInstance(flagTime)
    setOnMenuItemClickListener {
        baseDebounce.interval(this, baseDebounce, iEvent)
        true
    }
}

// MaterialToolbar 点击事件间隔 默认1秒
fun MaterialToolbar.navigateIconClickGap(isGlobal: Boolean = true, flagTime: Long = BASE_FLAG_TIME_400, msg: String? = null, iEven: IBaseDebounce<MaterialToolbar>) {
    val baseDebounce = if (isGlobal) BaseDebounce.getSIngleInstance() else BaseDebounce.newInstance(flagTime)
    setNavigationOnClickListener {
        iEven.onSuccess(baseDebounce.getIntervalResult(this, msg, baseDebounce) ?: return@setNavigationOnClickListener)
    }
}

// BaseEvent通用事件回调间隔 默认1秒，需手动创建EventGapTimeExt对象
fun BaseDebounce.interval(msg: String? = null, iEvent: IBaseDebounce<BaseDebounce>) : BaseDebounce? {
    iEvent.onSuccess(getIntervalResult(this, msg, this) ?: return null)
    return this
}

// BaseEvent扩展 onFailure
fun BaseDebounce.interval(iEvent: IBaseDebounceExt<BaseDebounce>) : Boolean {
    return interval(this, this, iEvent)
}

fun BaseDebounce.interval(mHandler: Handler?, runnable: Runnable): BaseDebounce {
    mHandler?.postDelayed({ runnable.run() }, mCurrentFlagTime)
    return this
}

fun BaseDebounce.resetDuration(flagTime: Long) { mCurrentFlagTime = flagTime }


/*
// BaseEvent扩展 onFailure 使用内联
inline fun SwipeRefreshLayout.setAutoCancelRefreshing(lifecycleOwner: LifecycleOwner, cancelTime: Long = 5_000L, crossinline block: () -> Unit) {
    setOnRefreshListener {
        block()
        lifecycleOwner.lifecycleScope.launch {
            delay(cancelTime)
            isRefreshing = false
        }
    }
}

// SwipeRefreshLayout 使用内联扩展刷新事件
inline fun SwipeRefreshLayout.doOnCoroutineRefreshListener(delayMs: Long = 0, lifecycleOwner: LifecycleOwner, crossinline block: suspend CoroutineScope.() -> Unit) {
    setOnRefreshListener {
        lifecycleOwner.lifecycleScope.launch {
            block(this)
            if (delayMs != 0L) delay(delayMs)
            isRefreshing = false
        }
    }
}

// SwipeRefreshLayout 使用内联扩展自动刷新
inline fun SwipeRefreshLayout.doOnCoroutineAutoRefresh(delayMs: Long = 0, lifecycleOwner: LifecycleOwner, crossinline block: suspend CoroutineScope.() -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        isRefreshing = true
        block(this)
        if (delayMs != 0L) delay(delayMs)
        isRefreshing = false
    }
}
*/

// 用于简化对 EditText 组件设置 afterTextChanged 操作的扩展函数。
inline fun EditText.afterTextChanged(crossinline afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) { afterTextChanged.invoke(editable.toString()) }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun MaterialSwitch.setOnCheckedInterval(isGlobal:Boolean = true, flagTime: Long = BASE_FLAG_TIME_400, msg: String? = null, iEven: IBaseDebounce<MaterialSwitch>) {
    val baseDebounce = if (isGlobal) BaseDebounce.getSIngleInstance() else BaseDebounce.newInstance(flagTime)
    setOnCheckedChangeListener { buttonView, isChecked -> iEven.onSuccess(baseDebounce.getIntervalResult(this, msg, baseDebounce) ?: return@setOnCheckedChangeListener) }
}


fun String.setGloabalVal(value: Boolean) { BaseDebounce.getSIngleInstance().setBoolean(this, value) }
fun String.getGloabalVal():Boolean? { return BaseDebounce.getSIngleInstance().getBoolean(this) }