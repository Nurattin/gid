package com.travel.gid.utils.home_btns_controller

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.travel.gid.R
import com.travel.gid.utils.valueToDp


class HomeButtonsControllerImpl(var context: Context): HomeButtonsController {

    private lateinit var homeButtonIterator : HomeButtonsContainerIteractor

    val buttonScaleChecked = 1.0f
    val buttonScaleUnChecked = 1.0f

    lateinit var prevContainer: View
    var prevPosition = 0

    override fun setBtnStyleChecked(view: View, position: Int) {

        setBtnStyleUnChecked(prevContainer, prevPosition)
        prevContainer = view
        prevPosition = position

        homeButtonIterator = HomeButtonsContainerIteractor(view)
        homeButtonIterator.next()

        homeButtonIterator.mNodes.forEach {
            when(it){
                is ImageView -> {

                    it.setImageResource(getImageFromPosition(position))

                    it.background = ContextCompat.getDrawable(context, R.drawable.bg_btns_home_menu_check)

                    val container = it.parent as ViewGroup
                    val anim = ScaleAnimation(
                        buttonScaleUnChecked,
                        buttonScaleChecked,
                        buttonScaleUnChecked,
                        buttonScaleChecked,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f)
                    anim.duration = 300
                    anim.fillAfter = true



                    container.startAnimation(anim)

                }
                is TextView -> {
                    when(position) {
                        0 -> {
                            it.setTextColor(context.resources.getColor(R.color.black))
                        }
                        1 -> {
                            it.setTextColor(context.resources.getColor(R.color.black))
                        }
                        2 -> {
                            it.setTextColor(context.resources.getColor(R.color.black))
                        }
                        3 -> {
                            it.setTextColor(context.resources.getColor(R.color.black))
                        }
                    }
                }
            }
        }
    }

    fun getImageFromPosition(position: Int): Int{
         when(position) {
            0 ->    return R.drawable.ic_globe

            1 ->    return R.drawable.ic_gid_check

            2 ->    return R.drawable.ic_place_check

            3 ->    return R.drawable.ic_bascket_check
         }
        return R.drawable.banner_bg
    }

    override fun setBtnStyleUnChecked(view: View, position: Int) {
        homeButtonIterator = HomeButtonsContainerIteractor(view)
        homeButtonIterator.next()

        homeButtonIterator.mNodes.forEach {
            when(it){
                is ImageView -> {
                    when(position) {
                        0 -> {
                            it.setImageResource(R.drawable.ic_globe_uncheck)
                        }
                        1 -> {
                            it.setImageResource(R.drawable.ic_gid_uncheck)
                        }
                        2 -> {
                            it.setImageResource(R.drawable.ic_place_uncheck)
                        }
                        3 -> {
                            it.setImageResource(R.drawable.ic_bascket_uncheck)
                        }
                    }

                    it.background = ContextCompat.getDrawable(context, R.drawable.bg_btns_home_menu_uncheck)

                    it.layoutParams.width = valueToDp(76.0f, context)
                    it.requestLayout()

                }
                is TextView -> {
                    when(position) {
                        0 -> {
                            it.setTextColor(context.resources.getColor(R.color.text_menu_btn))
                        }
                        1 -> {
                            it.setTextColor(context.resources.getColor(R.color.text_menu_btn))
                        }
                        2 -> {
                            it.setTextColor(context.resources.getColor(R.color.text_menu_btn))
                        }
                        3 -> {
                            it.setTextColor(context.resources.getColor(R.color.text_menu_btn))
                        }
                    }
                }
            }
        }
    }
}

