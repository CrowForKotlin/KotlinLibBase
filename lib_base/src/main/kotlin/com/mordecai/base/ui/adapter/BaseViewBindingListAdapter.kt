package com.mordecai.base.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/cn/barry/base/adapter
 * @Time: 2022/5/6 18:08
 * @Author: CrowForKotlin
 * @Description: BaseRvListAdapter
 * @formatter:off
 **************************/

abstract class BaseViewBindingListAdapter<T: Any, VB : ViewBinding>(
    inline val getContentIsSame : (oldItem: T, newItem: T) -> Boolean
) : ListAdapter<T, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem === newItem
        override fun areContentsTheSame(oldItem: T, newItem: T) = getContentIsSame(oldItem, newItem)
    })
{
    abstract fun getViewBinding(parent: ViewGroup): VB
}