package com.travel.gid.ui.delegation

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.utils.makeGone
import com.travel.gid.utils.makeVisibly
import io.supercharge.shimmerlayout.ShimmerLayout


interface Skeleton {
    fun showSkeleton(
        context: Context,
        recyclerView: RecyclerView,
        shimmerLayout: ShimmerLayout,
        orientation: Int,
        item: Int
    )

    fun stopSkeleton(
        recyclerView: RecyclerView, shimmerLayout: ShimmerLayout
    )
}


class SkeletonImpl() : Skeleton {
    override fun showSkeleton(
        context: Context,
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
                            View.inflate(context, item, null)

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
    }
}