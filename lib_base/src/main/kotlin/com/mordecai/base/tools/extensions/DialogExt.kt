@file:Suppress("unused")

package com.mordecai.base.tools.extensions

import android.app.AlertDialog
import android.content.Context
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.FloatRange
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/cn/barry/base/dialog
 * @Time: 2022/5/11 21:38
 * @Author: CrowForKotlin
 * @Description: Dialog
 * @formatter:off
 **************************/

fun interface IMaterialDialogCallback {
    fun doOnConfig(builder: MaterialAlertDialogBuilder)
}

inline fun Context.newDialog(dialogConfig: AlertDialog.Builder.() -> Unit) {
    AlertDialog.Builder(this).apply {
        dialogConfig()
        create()
        show()
    }
}

fun Context.newMaterialDialog(iMaterialDialogCallback: IMaterialDialogCallback): androidx.appcompat.app.AlertDialog {
    val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
    iMaterialDialogCallback.doOnConfig(materialAlertDialogBuilder)
    val dialog = materialAlertDialogBuilder.create()
    dialog.show()
    return dialog
}

// 重写onStart并在内部使用
fun Window.setLayoutWidthAndHeight(@FloatRange(from = 0.0, to = 1.0) width: Float, @FloatRange(from = 0.0, to = 1.0) height: Float) {
    val displayMetrics = context.resources.displayMetrics
    setLayout((displayMetrics.widthPixels * width).toInt(), (displayMetrics.heightPixels * height).toInt())
}

fun Window.setLayoutMatch() = setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

fun Window.setLayoutWarp() = setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

fun Window.setBackgroundTransparent() = setBackgroundDrawableResource(android.R.color.transparent)

fun Window.setMaskAmount(@FloatRange(from = 0.0, to = 1.0) amount: Float) = setDimAmount(amount)

/*suspend inline fun showAppDialog(
    crossinline onDialogConfig: () -> Unit,
    crossinline onDialog: androidx.appcompat.app.AlertDialog.() -> Unit
) {
    FlowBus.with<IMaterialDialog>(APP_DIALOG)
        .post( object : IMaterialDialog {
            override fun onDialogConfig(dialog: MaterialAlertDialogBuilder) { onDialogConfig() }
            override fun onDialog(dialog: androidx.appcompat.app.AlertDialog) { dialog.onDialog() }
        })
}*/


