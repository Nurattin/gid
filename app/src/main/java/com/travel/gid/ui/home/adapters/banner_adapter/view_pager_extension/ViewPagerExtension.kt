package com.travel.gid.ui.home.adapters.banner_adapter.view_pager_extension

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.travel.gid.ui.home.adapters.banner_adapter.ViewPagerAdapter
import kotlin.math.abs

// Добаволяет viewPager функцию авто скролла



fun ViewPager2.autoScrollViewPager(
    adapters: ViewPagerAdapter,
) {
    val compositePageTransformer = CompositePageTransformer()
    compositePageTransformer.addTransformer(MarginPageTransformer(30))
    compositePageTransformer.addTransformer { page, position ->
        val r = 1 - abs(position)
        page.scaleY = 0.85f + r * 0.15f
    }
    setCurrentItem(1, false)
    adapter = adapters
    clipToPadding = false
    clipChildren = false
    offscreenPageLimit = 3
    getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    setPageTransformer(compositePageTransformer)

}

// Добаволяет viewPager функцию бесконечного скролла
fun ViewPager2.infinityScrollViewPager() {
    val recyclerView = getChildAt(0) as RecyclerView
    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    val itemCount = adapter?.itemCount ?: 0

    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(
            recyclerView: RecyclerView, dx: Int, dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
            val lastItemVisible = layoutManager.findLastVisibleItemPosition()
            if (firstItemVisible == (itemCount - 1) && dx > 0) {
                recyclerView.scrollToPosition(1)
            } else if (lastItemVisible == 0 && dx < 0) {
                recyclerView.scrollToPosition(itemCount - 2)
            }
        }
    })
}