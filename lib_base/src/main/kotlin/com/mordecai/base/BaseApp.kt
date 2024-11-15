package com.mordecai.base

import android.app.Application
import com.mordecai.base.tools.extensions.log
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import kotlin.system.measureTimeMillis

open class BaseApp : Application() {

    companion object { lateinit var context: Application }

    open fun KoinApplication.loadModules() {}

    override fun onCreate() {
        super.onCreate()
        context = this
        measureTimeMillis {
            startKoin {
                androidContext(this@BaseApp)
                this.loadModules()
            }
        }.log()
    }
}