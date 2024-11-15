package com.mordecai.base.ui.view.viewstub


import android.view.View
import androidx.viewbinding.ViewBinding

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Package: com.mordecai.base.ui.view
 * @Time: 2023/10/29 21:02
 * @Author: CrowForKotlin
 * @Description: BaseViewStub
 * @formatter:on
 **************************/
fun interface IBaseViewStub<VB : ViewBinding> {
    fun onBinding(view: View, binding: VB)
}