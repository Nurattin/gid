package com.travel.gid.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val space: Int = 0,
    private val orientation: Orientation = Orientation.VERTICAL,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) > 0) { // Пропускаем отступ для первого элемента
            outRect.top = if (orientation == Orientation.VERTICAL) space else 0
            outRect.left = if (orientation == Orientation.HORIZONTAL) space else 0
        }
    }

    enum class Orientation { VERTICAL, HORIZONTAL }
}

class SpacingItemDecorator2 (private var padding: Int, val context: Context) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    )
    {
        val resources = context.resources
        val paddingNew = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            padding.toFloat(),
            resources.displayMetrics
        ).toInt()

        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = paddingNew
        outRect.right = paddingNew
    }
}

class SpacingItemDecorator (private var padding: Int, val context: Context) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    )
    {
        val resources = context.resources
        val paddingNew = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            padding.toFloat(),
            resources.displayMetrics
        ).toInt()

        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = paddingNew
        outRect.bottom = paddingNew
        outRect.left = paddingNew
        outRect.right = paddingNew
    }
}
