package com.travel.gid.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.travel.gid.data.models.DirectionData
import com.travel.gid.ui.home.child_fragments.direction_fragment.DirectionsFragment
import com.travel.gid.ui.home.child_fragments.event_fragment.EventFragment
import com.travel.gid.ui.home.child_fragments.gid_fragment.GidFragment

class ViewPagerChildFragmentsAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        val fragment = when (position) {
            0 -> DirectionsFragment()
            1 -> GidFragment()
            2 -> EventFragment()
            else -> DirectionsFragment()
        }
        return fragment
    }

}