package com.travel.gid.utils.home_btns_controller

import android.view.View
import android.view.ViewGroup

import android.widget.AdapterView
import java.util.*


class HomeButtonsContainerIteractor(view: View?) : MutableIterator<View?> {

    val mNodes: Stack<View> = Stack<View>()

    override fun hasNext(): Boolean {
        return !mNodes.empty()
    }


    override fun next(): View {
        var view: View? = null

        if (hasNext()) {

            view = mNodes.pop()
            assert(view != null)

            if (view is ViewGroup || view is AdapterView<*>) {
                val viewGroup = view as ViewGroup?
                for (i in 0 until viewGroup!!.childCount) {
                    assert(viewGroup.getChildAt(i) != null)
                    mNodes.push(viewGroup.getChildAt(i))
                }
            }
        }

        return view!!
    }

    override fun remove() {
        // not supported
    }

    init {
        if (view != null) {
            mNodes.push(view)
        }
    }
}