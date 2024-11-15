package com.mordecai.base.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/com/barry/base/fragment
 * @Time: 2022/11/14 20:15
 * @Author: CrowForKotlin
 * @Description: BaseVBFragmentImpl
 * @formatter:off
 **************************/
abstract class BaseFragmentImpl : Fragment(), IBaseFragment {

    /** UI Handler */
    protected val mHandler by lazy { Handler(Looper.getMainLooper()) }

    // 初始化View
    override fun initView(savedInstanceState: Bundle?) {}

    // 初始化监听事件
    override fun initListener() {}

    // 初始化数据
    override fun initData(savedInstanceState: Bundle?) { }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getView(inflater, container, savedInstanceState)
    }

    /**
     * Fragment在销毁时需要对引用进行置空防止泄漏，如果Fragment走重启状态，并且Observer中 启用了lifecycleScope，那么需要在Observer前初始化引用数据
     * 否则Observer拿到的引用数据为空
     *
     * 2024-02-16 23:33:26 周五 下午
     * @author crowforkotlin
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        initObserver(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initData(savedInstanceState)
        initListener()
    }

    override fun onDestroyView() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }

    inline fun viewLifecycleScope(coroutineContext: CoroutineContext? = null, initializeDelay: Long? = null, crossinline scope: suspend CoroutineScope.() -> Unit):Job {
        return if (coroutineContext == null) {
            viewLifecycleOwner.lifecycleScope.launch {
                if (initializeDelay != null) { delay(initializeDelay) }
                scope()
            }
        } else {
            viewLifecycleOwner.lifecycleScope.launch(coroutineContext) {
                if (initializeDelay != null) { delay(initializeDelay) }
                scope()
            }
        }
    }
}
