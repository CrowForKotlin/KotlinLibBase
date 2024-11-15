package com.mordecai.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.mordecai.base.tools.extensions.doOnError
import com.mordecai.base.tools.extensions.doOnLoading
import com.mordecai.base.tools.extensions.doOnSuccess
import com.mordecai.base.tools.extensions.toast
import com.mordecai.base.ui.viewmodel.BaseViewModel

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/cn/barry/base/fragment
 * @Time: 2022/5/3 9:04
 * @Author: CrowForKotlin
 * @Description: BaseVBFragment
 * @formatter:on
 **************************/
abstract class BaseMvvmFragment<VB : ViewBinding, out VM : BaseViewModel> : BaseFragmentImpl() {

    private var _mBinding: VB? = null
    protected val mBinding get() = _mBinding!!
    protected val mViewModel by lazy { getViewModel().value }

    abstract fun getViewBinding(inflater: LayoutInflater): VB
    abstract fun getViewModel(): Lazy<VM>
    abstract fun showLoadingAnim()
    abstract fun dismissLoadingAnim()

    override fun initObserver(saveInstanceState: Bundle?) {
        mViewModel.mBaseViewState.observe(viewLifecycleOwner) { viewState ->
            viewState
                .doOnLoading{ showLoadingAnim() }
                .doOnSuccess { dismissLoadingAnim() }
                .doOnError { _, msg ->
                    dismissLoadingAnim()
                    if (msg?.isNotBlank() == true) toast(msg)
                }
        }
    }

    override fun initListener() {}
    override fun initView(savedInstanceState: Bundle?) {}
    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }
    override fun getView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getViewBinding(inflater).also { _mBinding = it }.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver(savedInstanceState)
    }
}