package com.mordecai.base.tools.extensions

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import com.mordecai.base.ui.activity.BaseActivityImpl
import com.mordecai.base.ui.fragment.BaseFragmentImpl
import com.mordecai.base.ui.viewmodel.BaseViewModelStore
import com.mordecai.base.ui.viewmodel.mvi.BaseMviViewModel
import org.koin.android.ext.android.getKoinScope
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.viewmodel.resolveViewModel

/**
 * @Description: ViewModel Ext
 * @author lei , edit by crowforkotlin
 * @date 2023-09-05 23:55:31 周二 下午
 */

/**
 * 全局ViewModel For Activity
 *
 * 2023-09-06 00:11:05 周三 上午
 */
@MainThread
inline fun <reified VM : BaseMviViewModel<*>> BaseFragmentImpl.globalViewModel(): Lazy<VM> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getViewModel<VM>(
            qualifier = null,
            ownerProducer = { BaseViewModelStore },
            parameters = null
        )
    }
}

/**
 * 全局ViewModel For Activity
 *
 * 2023-09-06 00:11:05 周三 上午
 */
@MainThread
inline fun <reified VM : BaseMviViewModel<*>> BaseActivityImpl.globalViewModel(): Lazy<VM> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getViewModel<VM>(
            qualifier = null,
            viewModelStoreOwner = BaseViewModelStore,
            parameters = null
        )
    }
}

/**
 * 全局ViewModel For Activity
 *
 * 2023-09-06 00:11:05 周三 上午
 */
@MainThread
inline fun <reified VM : BaseMviViewModel<*>> DialogFragment.globalViewModel(): Lazy<VM> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getViewModel<VM>(
            qualifier = null,
            ownerProducer = { BaseViewModelStore },
            parameters = null
        )
    }
}

/**
 * Retrieve ViewModel instance for ComponentActivity
 * @param qualifier
 * @param extrasProducer
 * @param parameters
 */
@OptIn(KoinInternalApi::class)
@MainThread
@PublishedApi
internal inline fun <reified T : ViewModel> ComponentActivity.getViewModel(
    qualifier: Qualifier? = null,
    viewModelStoreOwner: ViewModelStoreOwner,
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline parameters: (() -> ParametersHolder)? = null,
): T {
    return resolveViewModel(
        T::class,
        viewModelStore = viewModelStoreOwner.viewModelStore,
        extras = extrasProducer?.invoke() ?: this.defaultViewModelCreationExtras,
        qualifier = qualifier,
        parameters = parameters,
        scope = getKoinScope()
    )
}
