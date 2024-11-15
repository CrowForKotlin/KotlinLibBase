package com.mordecai.base.ui.view.viewstub


import android.view.View
import android.view.ViewStub
import androidx.lifecycle.Lifecycle
import com.mordecai.base.tools.extensions.setClickListener
import com.mordecai.base.databinding.BaseErrorLayoutBinding

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Package: com.mordecai.base.ui.view
 * @Time: 2023/10/29 21:02
 * @Author: CrowForKotlin
 * @Description: BaseViewStub
 * @formatter:on
 **************************/
class BaseErrorViewStub(
    viewStub: ViewStub,
    lifecycle: Lifecycle,
    mBinding: IBaseViewStub<BaseErrorLayoutBinding>
) : BaseViewStub<BaseErrorLayoutBinding>(
    mViewStub = viewStub,
    mLifecycle = lifecycle,
    mOnBinding = mBinding
) {
    /**
     * 为当前Lifecycle 添加观察此对象
     *
     * 2023-10-29 21:17:33 周日 下午
     * @author crowforkotlin
     */
    init { lifecycle.addObserver(this) }

    companion object {
        fun baseErrorViewStub(viewStub: ViewStub, lifecyle: Lifecycle, retry: () -> Unit): BaseErrorViewStub {
            return BaseErrorViewStub(
                viewStub =  viewStub,
                lifecycle = lifecyle,
                mBinding = { _, binding -> binding.retry.setClickListener { retry() } }
            )
        }
    }

    override fun bindViewBinding(view: View): BaseErrorLayoutBinding { return BaseErrorLayoutBinding.bind(view) }

}