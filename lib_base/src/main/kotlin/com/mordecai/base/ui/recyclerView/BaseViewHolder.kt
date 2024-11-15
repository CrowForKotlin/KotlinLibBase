package com.mordecai.base.ui.recyclerView

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * ● BaseViewHolder
 *
 * ● 2024/3/28 23:02
 * @author crowforkotlin
 * @formatter:on
 */
abstract class BaseViewHolder<VB: ViewBinding>(baseBinding: VB) : RecyclerView.ViewHolder(baseBinding.root)  {

}