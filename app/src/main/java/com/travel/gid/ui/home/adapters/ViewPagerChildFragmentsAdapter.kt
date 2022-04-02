package com.travel.gid.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.travel.gid.data.models.DirectionData
import com.travel.gid.ui.home.child_fragments.GidFragment
import com.travel.gid.ui.home.child_fragments.DirectionsFragment

class ViewPagerChildFragmentsAdapter(fragment: Fragment, var directions: List<DirectionData>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        var fragment :Fragment = DirectionsFragment()

        when (position){
            0 -> {
                fragment = DirectionsFragment.newInstance(directions)
            }
            1 -> {
                fragment = GidFragment()
            }
        }
        return fragment
    }

}