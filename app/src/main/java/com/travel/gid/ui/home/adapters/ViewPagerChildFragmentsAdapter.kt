package com.travel.gid.ui.home.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.travel.gid.data.models.DirectionData
import com.travel.gid.databinding.HomeFragmentBinding
import com.travel.gid.ui.home.HomeFragment
import com.travel.gid.ui.home.child_fragments.gid_fragment.GidFragment
import com.travel.gid.ui.home.child_fragments.direction_fragment.DirectionsFragment
import com.travel.gid.ui.home.child_fragments.event_fragment.EventFragment

class ViewPagerChildFragmentsAdapter(fragment: HomeFragment, private val listFragment: List<Fragment>) : FragmentStateAdapter(fragment) {


    override fun getItemCount() = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                listFragment[0]
            }
            1 -> {
                listFragment[1]

            }
            2 -> {
                listFragment[2]
            }

            else -> {listFragment[0]}
        }
    }
}