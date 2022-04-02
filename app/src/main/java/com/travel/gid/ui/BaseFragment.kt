package com.travel.gid.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.viewbinding.ViewBinding
import com.travel.gid.R

abstract class BaseFragment<B : ViewBinding> : Fragment() {
    protected val navController by lazy { (activity as ActivityProviding).provideNavController() }
    protected val insetController by lazy { (activity as ActivityProviding).provideInsetsController()  }
    protected var binding: B? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) {_, insets ->
            applyInsets(insets)
            insets
        }
    }

    open fun applyInsets(inset: WindowInsetsCompat){}

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        val defNavOption = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.slide_out)
            .build()
    }
}