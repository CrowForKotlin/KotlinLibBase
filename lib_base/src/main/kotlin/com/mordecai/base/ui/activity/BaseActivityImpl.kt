package com.mordecai.base.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mordecai.base.model.bean.BaseValueBean
import com.mordecai.base.tools.coroutine.FlowBus
import com.mordecai.base.tools.extensions.GlobalEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/com/barry/base/activity
 * @Time: 2022/11/14 19:20
 * @Author: CrowForKotlin
 * @Description: BaseViewBindingActivityImpl 实现
 * @formatter:on
 **************************/
abstract class BaseActivityImpl : AppCompatActivity() {

    init {
        FlowBus.withUnSafety<Any>(GlobalEvent).register(lifecycleScope) { event ->
            if (isDestroyed) return@register
            onGlobalEvent(event)
            when(event) {
                is BaseValueBean -> { get<Any>(named(event.mName)) { parametersOf( this, layoutInflater, lifecycleScope, event.mValue) } }
            }
        }
    }

    open fun onGlobalEvent(event: Any) {}
    open fun initData(savedInstanceState: Bundle?) {}

    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initListener()

    protected abstract fun getView(): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())
        initData(savedInstanceState)
        initView(savedInstanceState)
        initListener()
    }

    inline fun lifecycleScope(crossinline scope: suspend CoroutineScope.() -> Unit) { lifecycleScope.launch { scope() } }
}