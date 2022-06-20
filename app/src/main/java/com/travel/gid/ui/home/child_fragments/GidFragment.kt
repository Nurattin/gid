package com.travel.gid.ui.home.child_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.travel.gid.databinding.FragmentGidBinding
import com.travel.gid.ui.home.adapters.GidAdapter

class GidFragment : Fragment(){

    lateinit var binding: FragmentGidBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentGidBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = GidAdapter()
        binding.run {
            gidRecycler.adapter = adapter
        }
    }
}