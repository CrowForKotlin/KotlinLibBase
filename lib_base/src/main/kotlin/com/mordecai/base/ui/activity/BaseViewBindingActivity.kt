package com.mordecai.base.ui.activity

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding

/*************************
 * @ProjectName: JetpackApp
 * @Dir_Path: lib_base/src/main/java/cn/barry/base/activity
 * @Time: 2022/4/26 9:32
 * @Author: CrowForKotlin
 * @Description: BaseViewBindingActivity 父类
 * @formatter:on
 **************************/
abstract class BaseViewBindingActivity<out VB : ViewBinding> : BaseActivityImpl() {

    protected val mBinding by lazy { getViewBinding() }

    abstract fun getViewBinding(): VB

    override fun getView(): View = mBinding.root
}