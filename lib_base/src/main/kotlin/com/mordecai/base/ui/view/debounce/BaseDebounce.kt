@file:Suppress("MemberVisibilityCanBePrivate")

package com.mordecai.base.ui.view.debounce

import com.mordecai.base.model.bean.BaseDebounceBean
import com.mordecai.base.tools.extensions.toast
import kotlin.math.absoluteValue


/**
 * 事件基类
 *
 * time: 2023-09-10 23:11:17 周日 下午
 * @author:crow
 */
open class BaseDebounce private constructor(mFlagTime: Long) {

    private var mFlagMap: MutableMap<String, Boolean>? = null
    private var mInitOnce: Boolean = false
    private var mLastClickGapTime: Long = 0L
    private var mCurrentTime: Long = 0L
    var mCurrentFlagTime = mFlagTime
        internal set

    companion object {

        const val BASE_FLAG_TIME_400 = 400L
        const val BASE_FLAG_TIME_300 = 300L

        private var mBaseDebounce: BaseDebounce? = null

        fun newInstance(initFlagTime: Long = BASE_FLAG_TIME_300): BaseDebounce {
            return BaseDebounce(initFlagTime)
        }

        fun getSIngleInstance(): BaseDebounce {
            if (mBaseDebounce == null) {
                synchronized(this) {
                    if (mBaseDebounce == null) {
                        mBaseDebounce = BaseDebounce(BASE_FLAG_TIME_300)
                    }
                }
            }
            return mBaseDebounce!!
        }
    }

    private fun initFlagMap() {
        if (mFlagMap == null) mFlagMap = mutableMapOf()
    }

    /**
     * 处理事件间隔实现
     *
     * 2023-09-10 23:09:46 周日 下午
     */
    internal fun <T> getIntervalResult(
        type: T,
        msg: String? = null,
        baseDebounce: BaseDebounce
    ): BaseDebounceBean<T>? {
        baseDebounce.mCurrentTime = System.currentTimeMillis()
        return if (baseDebounce.mCurrentTime - baseDebounce.mLastClickGapTime > mCurrentFlagTime) {
            baseDebounce.mLastClickGapTime = baseDebounce.mCurrentTime
            BaseDebounceBean(type, baseDebounce)
        } else {
            if (msg != null) toast(msg)
            null
        }
    }

    /**
     * 处理事件间隔
     *
     * 2023-09-10 23:10:36 周日 下午
     */
    internal fun <T> interval(
        type: T,
        baseDebounce: BaseDebounce,
        iEven: IBaseDebounceExt<T>
    ) : Boolean {
        val result = getIntervalResult(type, null, baseDebounce)
        return if (result != null) {
            iEven.onSuccess(result)
            true
        } else {
            iEven.onFailure(getGapTime())
            false
        }
    }

    internal fun getGapTime() = (mCurrentFlagTime - (mCurrentTime - mLastClickGapTime)).absoluteValue

    fun eventInitLimitOnce(runnable: Runnable): Unit? {
        if (!mInitOnce) {
            mInitOnce = true
            runnable.run()
            return Unit
        }
        return null
    }

    fun eventInitLimitOnceByTag(tag: String, runnable: Runnable) {
        initFlagMap()
        val value = mFlagMap!![tag]
        if (value == null || !value) {
            mFlagMap!![tag] = true
            runnable.run()
        }
    }

    fun setBoolean(tag: String, defaultValue: Boolean = false) {
        initFlagMap()
        mFlagMap!![tag] = defaultValue
    }

    fun getBoolean(tag: String): Boolean? {
        initFlagMap()
        return mFlagMap!![tag]
    }

    fun remove(tag: String) {
        mFlagMap?.remove(tag)
    }

    fun remove(vararg tag: String) {
        if (mFlagMap == null) return
        tag.forEach { mFlagMap!!.remove(it) }
    }

    fun clear() {
        mFlagMap?.clear()
    }
}