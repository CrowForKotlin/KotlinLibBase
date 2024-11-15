package com.mordecai.base.tools.extensions

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

/*************************
 * @ProjectName: JetpackApp
 * @Dir_Path: lib_base/src/main/java/cn/barry/base
 * @Time: 2022/3/8 10:37
 * @Author: CrowForKotlin
 * @Description: Activity Ext
 **************************/
inline fun <reified T> Context.startActivity() = startActivity(Intent(this, T::class.java))

inline fun <reified T> Context.startActivity(lambda: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.lambda()
    startActivity(intent)
}

fun AppCompatActivity.repeatOnLifecycle(state: Lifecycle.State = Lifecycle.State.STARTED, lifecycleStateCallBack: LifecycleStateCallBack) {
    lifecycleScope.launch { repeatOnLifecycle(state) { lifecycleStateCallBack.onLifeCycle(this) } }
}

fun ComponentActivity.repeatOnLifecycle(state: Lifecycle.State = Lifecycle.State.STARTED, lifecycleStateCallBack: LifecycleStateCallBack) {
    lifecycleScope.launch { repeatOnLifecycle(state) { lifecycleStateCallBack.onLifeCycle(this) } }
}

fun FragmentActivity.repeatOnLifecycle(state: Lifecycle.State = Lifecycle.State.STARTED, lifecycleStateCallBack: LifecycleStateCallBack) {
    lifecycleScope.launch { repeatOnLifecycle(state) { lifecycleStateCallBack.onLifeCycle(this) } }
}
