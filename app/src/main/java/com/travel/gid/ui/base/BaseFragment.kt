package com.travel.gid.ui.base

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.travel.gid.R
import com.travel.gid.ui.ActivityProviding
import com.travel.gid.ui.base.loader.Error
import com.travel.gid.ui.base.loader.LoaderProgressBar
import com.travel.gid.ui.base.loader.Skeleton
import com.travel.gid.utils.*
import io.supercharge.shimmerlayout.ShimmerLayout

abstract class BaseFragment<T : ViewBinding> : Fragment(), Skeleton, LoaderProgressBar,
    Error {

    protected var mainContent: View? = null
    protected var errorInternetContainer: View? = null
    protected var refreshData: SwipeRefreshLayout? = null
    protected var progressBar: ProgressBar? = null
    protected var skeletonLayout: LinearLayout? = null
    protected var shimmer: ShimmerLayout? = null
    protected var recyclerView: RecyclerView? = null
    protected var itemLayoutSkeleton: Int? = null

    protected val navController by lazy { (activity as ActivityProviding).provideNavController() }
    protected val insetController by lazy { (activity as ActivityProviding).provideInsetsController() }

    protected var binding
        get() = _binding!!
        set(value) {
            _binding = value
        }

    private var _binding: T? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            applyInsets(insets)
            insets
        }
    }

    open fun applyInsets(inset: WindowInsetsCompat) {}


    companion object {
        val defNavOption = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.slide_out)
            .build()
    }


    override fun showError(
    ) {
        mainContent?.makeGone()
        errorInternetContainer?.makeVisibly()
        refreshData?.stopRefreshed()
    }

    override fun showProgressBar(
    ) {
        mainContent?.makeGone()
        progressBar?.makeVisibly()
    }

    override fun stopProgressBar(
    ) {
        mainContent?.makeVisibly()
        errorInternetContainer?.makeGoneIfVisibly()
        refreshData?.stopRefreshed()
    }

    override fun showSkeleton(
        recyclerView: RecyclerView,
        shimmerLayout: ShimmerLayout,
        orientation: Int,
        item: Int
    ) {
        recyclerView.makeGone()
        val skeletonLayout = shimmerLayout.getChildAt(0) as LinearLayout
        skeletonLayout.let {
            it.orientation = orientation
            if (it.isEmpty()) {
                repeat(2) {
                    item.let { item ->
                        skeletonLayout.addView(
                            layoutInflater.inflate(
                                item, null
                            )
                        )
                    }
                }
            }
        }
        shimmerLayout.makeVisibly()
        shimmerLayout.startShimmerAnimation()
    }

    override fun stopSkeleton(recyclerView: RecyclerView, shimmerLayout: ShimmerLayout) {
        recyclerView.makeVisibly()
        shimmerLayout.makeGone()
        shimmerLayout.stopShimmerAnimation()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}