package com.mordecai.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.mordecai.base.tools.extensions.buildDefaultNavOption
import com.mordecai.base.tools.extensions.immersionPadding
import com.mordecai.base.tools.extensions.log
import com.mordecai.base.tools.extensions.repeatOnLifecycle
import com.mordecai.base.tools.extensions.updatePadding
import com.mordecai.base.ui.viewmodel.mvi.BaseMviIntent
import com.mordecai.base.ui.viewmodel.mvi.BaseMviSuspendResult
import com.mordecai.base.ui.viewmodel.mvi.BaseMviViewModel
import com.mordecai.base.ui.viewmodel.mvi.IBaseMvi
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

abstract class BaseMviFragment<out VB : ViewBinding> : BaseFragmentImpl(), IBaseMvi {

    private var _mBinding: VB? = null
    protected val mBinding get() = _mBinding!!

    private var _mContext: Context? =  null
    protected val mContext: Context get() = _mContext!!

    protected var mBackDispatcher: OnBackPressedCallback? = null

    abstract fun getViewBinding(inflater: LayoutInflater): VB

    override fun initObserver(saveInstanceState: Bundle?) {}

    override fun initListener() {}

    override fun initView(savedInstanceState: Bundle?) {}

    override fun <I : BaseMviIntent> BaseMviViewModel<I>.onOutput(state: Lifecycle.State, baseMviSuspendResult: BaseMviSuspendResult<I>) {
        repeatOnLifecycle(state) { output { intent -> baseMviSuspendResult.onResult(intent) } }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mContext = requireContext()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = getViewBinding(inflater)
        _mBinding = view
        return view.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
        _mContext = null
        mBackDispatcher?.remove()
        mBackDispatcher = null
        get<Unit>(named("Destroy"))
    }

    protected fun immersionRoot() {
        // 设置 内边距属性 实现沉浸式效果
        immersionPadding(mBinding.root) { view, insets, _ ->
            view.updatePadding(top = insets.top, bottom = insets.bottom, left = insets.left, right = insets.right)
        }
    }

    protected inline fun fragmentScope(noinline orElse: (() -> Unit)? = null, scope: () -> Unit) {
        if (isAdded && !isDetached) {
            scope()
        } else {
            orElse?.invoke()
        }
    }
    protected inline fun fragmentResumeScope(noinline orElse: (() -> Unit)? = null, scope: () -> Unit) {
       fragmentScope {
           if (isResumed) {
               scope()
           }
       }
    }
}