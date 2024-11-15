package com.mordecai.base.ui.view.popupwindow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mordecai.base.tools.extensions.app
import com.mordecai.base.databinding.BaseTextviewBinding

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/com/barry/base/view
 * @Time: 2022/11/20 10:43
 * @Author: CrowForKotlin
 * @Description: ToolTipsView
 * @formatter:on
 **************************/
class BaseTooltipsView(context: Context, layoutInflater: LayoutInflater) {

    companion object {

        private var mBaseTooltipsView: BaseTooltipsView? = null
        private fun getToolTipsView(): BaseTooltipsView {
            mBaseTooltipsView = mBaseTooltipsView ?: BaseTooltipsView(app, LayoutInflater.from(app))
            return mBaseTooltipsView!!
        }

        fun showToolTips(view: View, content: String, offsetX: Int = 0, offsetY: Int = 0) {
            val toolTipsView = getToolTipsView()
            toolTipsView.binding.textview.text = content
            toolTipsView.popupWindow.update()                     // 更新后就可以点击提示视图外部取消提示
            toolTipsView.popupWindow.showAsDropDown(view, offsetX, offsetY)
        }

        fun showTipsWhenLongClick(view: TextView, offsetX: Int = 0, offsetY: Int = 0) {
            view.setOnLongClickListener {
                val toolTipsView = getToolTipsView()
                toolTipsView.binding.textview.text = view.text
                toolTipsView.popupWindow.update()                     // 更新后就可以点击提示视图外部取消提示
                toolTipsView.popupWindow.showAsDropDown(view, offsetX, offsetY)
                true
            }
        }

        fun showTipsWhenLongClick(view: View, text: CharSequence, offsetX: Int = 0, offsetY: Int = 0) {
            view.setOnLongClickListener {
                val toolTipsView = getToolTipsView()
                toolTipsView.binding.textview.text = text.toString()
                toolTipsView.popupWindow.update()                     // 更新后就可以点击提示视图外部取消提示
                toolTipsView.popupWindow.showAsDropDown(view, view.measuredWidth / 2, offsetY)
                true
            }
        }
    }

    private val binding by lazy { BaseTextviewBinding.inflate(layoutInflater) }
    private val popupWindow by lazy {
        PopupWindow(context).apply {
            contentView = binding.root
            isFocusable = false         // 聚焦取消
            isOutsideTouchable = true   // 允许外部触摸
            setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent))
        }
    }
}