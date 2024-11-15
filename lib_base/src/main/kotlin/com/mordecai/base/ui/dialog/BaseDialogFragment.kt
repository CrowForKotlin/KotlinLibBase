package com.mordecai.base.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.mordecai.base.tools.extensions.error
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseDialogFragment<out VB: ViewBinding>: AppCompatDialogFragment() {

    private var _mBinding: VB? = null
    protected val mBinding get() = _mBinding!!

    private var _mContext: Context? =  null
    protected val mContext: Context get() = _mContext!!
    open fun isApplyDefaultStyle() = true

    abstract fun getViewBinding(layoutInflater: LayoutInflater) : VB
    abstract fun MaterialAlertDialogBuilder.initDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        _mContext = requireContext()
        super.onCreate(savedInstanceState)
        if (isApplyDefaultStyle()) {
            setStyle(DialogFragment.STYLE_NORMAL, com.mordecai.base.R.style.BaseThemeDialogFragmentWithoutDim)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        _mBinding = getViewBinding(layoutInflater)
        val builder = MaterialAlertDialogBuilder(requireContext(), theme)
        builder.initDialog()
        builder.setView(mBinding.root)
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mContext = null
        _mBinding = null
    }
}