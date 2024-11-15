package com.mordecai.base.test

import com.mordecai.base.BaseApp
import com.mordecai.base.test.ui.MainActivity
import com.mordecai.base.tools.extensions.log
import com.mordecai.base.tools.extensions.toast
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.definition.KoinDefinition
import org.koin.core.lazyModules
import org.koin.core.qualifier.named
import org.koin.dsl.ScopeDSL
import org.koin.dsl.lazyModule
import org.koin.dsl.module

class App : BaseApp() {

    override fun KoinApplication.loadModules() {
        lazyModules(
            listOf(
                lazyModule {
                    scope<MainActivity> {
                        getScopes().also { it.size.log() }
                    }
                }
            ),
            Dispatchers.Default
        )
        modules(
            module {
                single {  }
            }
        )
    }
    fun ScopeDSL.getScopes(): List<KoinDefinition<Unit>> {
        // 生成一个包含 30 个元素的列表，并对每个元素应用作用域
        return List(5000) { index ->
            scoped(named(index.toString())) {
                toast("HELLO : $index")
            }
        }.flatMap { listOf(it) } // 将每个元素重复两次，即实现 * 2 的效果
    }

}