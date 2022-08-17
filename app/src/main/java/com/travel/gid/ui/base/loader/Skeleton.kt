package com.travel.gid.ui.base.loader

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.supercharge.shimmerlayout.ShimmerLayout

interface Skeleton {
    fun showSkeleton(recyclerView: RecyclerView,
                     shimmerLayout: ShimmerLayout,
                     orientation: Int,
                     item: Int)
    fun stopSkeleton(recyclerView: RecyclerView, shimmerLayout: ShimmerLayout)
}