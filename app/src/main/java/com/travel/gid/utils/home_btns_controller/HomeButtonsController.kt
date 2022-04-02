package com.travel.gid.utils.home_btns_controller

import android.view.View

interface HomeButtonsController {
    fun setBtnStyleChecked(view: View, position: Int)
    fun setBtnStyleUnChecked(view: View, position: Int)
}