package com.mordecai.base.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mordecai.base.tools.extensions.immersionPadding
import com.mordecai.base.R
import com.mordecai.base.databinding.BaseLoadingBinding

/**
 * ● BaseLoadingFragment
 *
 * ● 2024/11/7 22:26
 * @author crowforkotlin
 * @formatter:on
 */
class BaseLoadingFragment : Fragment() {

    companion object {

        private const val TAG = "BaseLoadingFragment"

        fun show(fragmentManager: FragmentManager, @IdRes id: Int) {
            if (fragmentManager.findFragmentByTag(TAG) == null) {
                fragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.animator.nav_default_enter_anim,
                        R.animator.nav_default_exit_anim,
                        R.animator.nav_default_pop_enter_anim,
                        R.animator.nav_default_pop_exit_anim
                    )
                    .add(id, BaseLoadingFragment(), TAG)
                    .commit()
            }
        }

        fun dismiss(fragmentManager: FragmentManager) {
            fragmentManager.findFragmentByTag(TAG)
                ?.apply {
                    fragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.animator.nav_default_enter_anim,
                            R.animator.nav_default_exit_anim,
                            R.animator.nav_default_pop_enter_anim,
                            R.animator.nav_default_pop_exit_anim
                        )
                        .remove(this)
                        .commit()
                }
        }
    }

    private var _mBinding: BaseLoadingBinding? = null
    val mBinding: BaseLoadingBinding get() = _mBinding!!

    private var mBackDispatcher: OnBackPressedCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return BaseLoadingBinding.inflate(inflater, container, false)
            .also { _mBinding = it }
            .root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding.root.removeCallbacks()
        _mBinding = null
        mBackDispatcher?.remove()
        mBackDispatcher = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        immersionPadding(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
         mBackDispatcher = requireActivity().onBackPressedDispatcher.addCallback(this) { }
    }
}