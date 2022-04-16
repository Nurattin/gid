package com.travel.gid.ui.select_guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.travel.gid.databinding.BottomSheetSelectGuestBinding

class BottomSheetSelectGuests : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetSelectGuestBinding

    lateinit var childAdapter : ChildAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSelectGuestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childAdapter = ChildAdapter()

        binding.run {
            recyclerChild.adapter = childAdapter

            childPlus.setOnClickListener {
                if (childAdapter.count < 6) {
                    childAdapter.count += 1
                    childAdapter.notifyDataSetChanged()
                    childCount.text =  childAdapter.count.toString()
                }

            }

            childMinus.setOnClickListener {
                if (childAdapter.count > 0) {
                    childAdapter.count -= 1
                    childAdapter.notifyDataSetChanged()
                    childCount.text =  childAdapter.count.toString()
                }
            }
        }


    }


}