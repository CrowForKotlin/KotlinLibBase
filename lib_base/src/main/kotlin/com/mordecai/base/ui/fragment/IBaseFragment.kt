package com.mordecai.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/com/barry/base/fragment
 * @Time: 2022/10/4 16:37
 * @Author: CrowForKotlin
 * @Description: Interface Base Fragment
 * @formatter:on
 **************************/
interface IBaseFragment {

    // 获取View
    fun getView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    // 初始化View
    fun initView(bundle: Bundle?)

    // 初始化监听事件
    fun initListener()

    // 初始化数据
    fun initData(savedInstanceState: Bundle?)

    // 初始化观察者
    fun initObserver(saveInstanceState: Bundle?)
}