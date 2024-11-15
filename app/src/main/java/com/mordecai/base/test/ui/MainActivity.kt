package com.mordecai.base.test.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.mordecai.base.test.databinding.ActivityMainBinding
import com.mordecai.base.tools.extensions.log
import com.mordecai.base.ui.activity.BaseMviActivity
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.awaitAllStartJobs
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainActivity : BaseMviActivity<ActivityMainBinding>(), AndroidScopeComponent {

    override val scope: Scope by activityScope()

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        val scopes = scope.get<MainActivity>()
        lifecycleScope {
            delay(1000)
            getKoin().awaitAllStartJobs()
            scopes.get<Unit>(named("1"))
        }
    }
}