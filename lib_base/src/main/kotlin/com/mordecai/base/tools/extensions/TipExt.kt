@file:Suppress("unused")

package com.mordecai.base.tools.extensions

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Tip Util
 *
 * time: 2022/5/1 20:58 周日 下午
 * @author:crow
 */
const val TIPS_TAG = "BaseMordecai"

fun Any?.log(level: Int = Log.INFO, tag: String = TIPS_TAG) {
    if (this == null) {
        Log.e(tag, NullPointerException("Value is null.").stackTraceToString())
        return
    }
    when(level) {
        Log.ERROR -> Log.e(tag,"$this")
        Log.DEBUG -> Log.d(tag,"$this")
        Log.WARN -> Log.w(tag,"$this")
        Log.INFO -> Log.i(tag,"$this")
        Log.VERBOSE -> Log.v(tag,"$this")
    }
}
fun Any?.info(tag: String = TIPS_TAG) { Log.i(tag, this.toString()) }
fun Any?.error(tag: String = TIPS_TAG) { Log.e(tag, this.toString()) }

private var mToast: Toast? = null
private var mToastHide: Boolean = false

/* String Toast */
fun toast(msg: String, isShort: Boolean = true, context: Context = app.applicationContext) {
    if (mToastHide) return
    mToast?.cancel()
    mToast = Toast.makeText(context, msg, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
    mToast?.show()
}

/* CharSequence Toast */
fun toast(charSequence: CharSequence, isShort: Boolean = true) {
    if (mToastHide) return
    mToast?.cancel()
    mToast = Toast.makeText(app.applicationContext, charSequence, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
    mToast?.show()
}

/* SnackBar Show 字符串参数 */
fun View.showSnackBar(text: String, actionText: String? = null, duration: Int = Snackbar.LENGTH_SHORT, actionBlock: ((snackBar: Snackbar) -> Unit)? = null) {
    val snackBar = Snackbar.make(this, text, duration)
    if (actionText != null && actionBlock != null) snackBar.setAction(actionText) { actionBlock(snackBar) }
    snackBar.show()
}

/* SnackBar Show 资源ID参数 */
fun View.showSnackBar(resId: Int, actionResId: Int? = null, duration: Int = Snackbar.LENGTH_SHORT, actionBlock: ((bar: Snackbar) -> Unit)? = null) {
    val snackBar = Snackbar.make(this, resId, duration)
    if (actionResId != null && actionBlock != null) snackBar.setAction(actionResId) { actionBlock(snackBar) }
    snackBar.show()
}

fun error(message: Any): Nothing = throw IllegalStateException(message.toString())