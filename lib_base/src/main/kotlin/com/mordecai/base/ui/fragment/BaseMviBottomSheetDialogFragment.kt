package com.mordecai.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.mordecai.base.tools.extensions.repeatOnLifecycle
import com.mordecai.base.ui.viewmodel.mvi.BaseMviIntent
import com.mordecai.base.ui.viewmodel.mvi.BaseMviSuspendResult
import com.mordecai.base.ui.viewmodel.mvi.BaseMviViewModel

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/kotlin/com/crow/base/fragment
 * @Time: 2023/3/15 12:34
 * @Author: CrowForKotlin
 * @Description: BaseMviBottomSheetDF
 * @formatter:on
 **************************/
abstract class BaseMviBottomSheetDialogFragment<out VB : ViewBinding> : BaseBottomSheetDialogFragmentImpl(), IBaseFragment {

    private var _mBinding: VB? = null
    protected val mBinding get() = _mBinding!!

    lateinit var mContext: Context

    abstract fun getViewBinding(inflater: LayoutInflater): VB

    fun <I : BaseMviIntent> BaseMviViewModel<I>.onOutput(state: Lifecycle.State = Lifecycle.State.CREATED, baseMviSuspendResult: BaseMviSuspendResult<I>) {
        repeatOnLifecycle(state) { output { intent -> baseMviSuspendResult.onResult(intent) } }
    }



    override fun getView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getViewBinding(inflater).also { _mBinding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mContext = requireContext()
        initObserver(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _mBinding = null
        super.onDestroyView()
    }

    override fun initView(bundle: Bundle?) { }

    override fun initListener() { }

    override fun initObserver(saveInstanceState: Bundle?) { }

}