package com.travel.gid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.R
import com.travel.gid.databinding.FragmentReservationBinding
import com.travel.gid.databinding.HomeFragmentBinding
import com.travel.gid.ui.tour_detail.adapters.TravelDatesAdapter

class FragmentReservation : Fragment() {

    companion object {
        fun newInstance() = FragmentReservation()
    }

    lateinit var otherCheckBox: CheckBox
    lateinit var motionLayout: MotionLayout

    private var paymentMethodsCheckBoxList = ArrayList<CheckBox>()
    private var paymentMethodsLayoutList = ArrayList<LinearLayout>()

    private lateinit var countPeoplePlusButton : ImageView
    private lateinit var countPeopleMinusButton : ImageView

    private var countPeople = 0

    var positionChecBox = 0

    private lateinit var binding: FragmentReservationBinding

    private lateinit var viewModel: FragmentReservationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReservationBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {

            applyBtn.setOnClickListener {
                Toast.makeText(context, "Ваша заявка принята, скоро с вами свяжется оператор", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.homeFragment)
            }

            paymentMethodsCheckBoxList.add(paymentMethodsCheckBox1)
            paymentMethodsCheckBoxList.add(paymentMethodsCheckBox2)
            paymentMethodsCheckBoxList.add(paymentMethodsCheckBox3)


            paymentMethodsLayoutList.add(paymentMethodsLayout1)
            paymentMethodsLayoutList.add(paymentMethodsLayout2)
            paymentMethodsLayoutList.add(paymentMethodsLayout3)

            paymentMethodsCheckBoxList[positionChecBox].isChecked = true

            countPeoplePlusButton = countPeoplePlus
            countPeopleMinusButton = countPeopleMinus

            clickPaymentMethodsLayoutList()

            clickCountPeople()

            otherCheckBox = paymentMethodsCheckBox3
            motionLayout = constraintLayout3

            setupDirectionRecycler()
            otherCheckBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    motionLayout.transitionToEnd()
                } else {
                    motionLayout.transitionToStart()
                }
            })
        }

    }

    private fun clickPaymentMethodsLayoutList() {
        paymentMethodsLayoutList[0].setOnClickListener {
            otherClickAnimation(0)
        }
        paymentMethodsLayoutList[1].setOnClickListener {
            otherClickAnimation(1)
        }
        paymentMethodsLayoutList[2].setOnClickListener {
            otherClickAnimation(2)
        }
    }

    private fun setupDirectionRecycler() {

        binding.run {
            categoriesRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val adapter = TravelDatesAdapter()

            categoriesRecyclerView.adapter = adapter
        }
    }

    private fun clickCountPeople () {
        countPeoplePlusButton.setOnClickListener{
            countPeople++
            binding.run {
                countPeopleText.text = ""+countPeople
            }

        }

        countPeopleMinusButton.setOnClickListener {
            if(countPeople > 0) {
                countPeople--
                binding.run {
                    countPeopleText.text = "" + countPeople
                }
            }
        }
    }

    private fun otherClickAnimation(position: Int) {
        if (positionChecBox != position) {
            paymentMethodsCheckBoxList[position].isChecked = true
            paymentMethodsCheckBoxList[positionChecBox].isChecked = false
            positionChecBox = position
        }

    }



}