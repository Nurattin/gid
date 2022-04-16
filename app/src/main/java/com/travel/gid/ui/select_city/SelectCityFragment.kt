package com.travel.gid.ui.select_city

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.travel.gid.R
import com.travel.gid.databinding.SelectCityFragmentBinding
import com.travel.gid.ui.BaseFragment

class SelectCityFragment : BaseFragment<SelectCityFragmentBinding>() {

    companion object {
        fun newInstance() = SelectCityFragment()
    }

    private lateinit var viewModel: SelectCityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SelectCityFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }




}