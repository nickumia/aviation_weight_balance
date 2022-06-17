package com.ekpro.weightbalance.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ekpro.weightbalance.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val moment_empty: TextView = binding.textView12
        val moment_front: TextView = binding.textView13
        val moment_rear: TextView = binding.textView14
        val moment_zero: TextView = binding.textView15
        val moment_fuel: TextView = binding.textView26
        val moment_ramp: TextView = binding.textView19
        val moment_taxi: TextView = binding.textView27
        val moment_takeoff: TextView = binding.textView22
        val moment_used: TextView = binding.textView28
        val moment_landing: TextView = binding.textView25
        val weight_empty: EditText = binding.editTextNumberDecimal
        val weight_front: EditText = binding.editTextNumberDecimal3
        val weight_rear: EditText = binding.editTextNumberDecimal5
        val weight_fuel: EditText = binding.editTextNumberDecimal7
        val weight_taxi: EditText = binding.editTextNumberDecimal9
        val weight_used: EditText = binding.editTextNumberDecimal11
        val arm_empty: EditText = binding.editTextNumberDecimal2
        val arm_front: EditText = binding.editTextNumberDecimal4
        val arm_rear: EditText = binding.editTextNumberDecimal6
        val arm_fuel: EditText = binding.editTextNumberDecimal8

        val weight_zero: TextView = binding.textView9
        val arm_zero: TextView = binding.textView10
        val weight_ramp: TextView = binding.textView17
        val arm_ramp: TextView = binding.textView18
        val weight_takeoff: TextView = binding.textView20
        val arm_takeoff: TextView = binding.textView21
        val weight_landing: TextView = binding.textView23
        val arm_landing: TextView = binding.textView24

        val arm_fuel2: TextView = binding.textView29
        val arm_fuel3: TextView = binding.textView30

        val button_zero: Button = binding.zero
        val button_ramp: Button = binding.ramp
        val button_takeoff: Button = binding.takeoff
        val button_landing: Button = binding.landing

        var v_weight_zero = 1.0F
        var v_moment_zero = 0.0F
        var v_moment_ramp = 0.0F
        var v_weight_ramp = 1.0F
        var v_moment_takeoff = 0.0F
        var v_weight_takeoff = 1.0F

        button_zero.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                var v_moment_empty = 0.0F
                var v_moment_front = 0.0F
                var v_moment_rear = 0.0F
                try {
                    v_moment_empty = weight_empty.text.toString().toFloat() * arm_empty.text.toString().toFloat()
                    v_moment_front = weight_front.text.toString().toFloat() * arm_front.text.toString().toFloat()
                    v_moment_rear = weight_rear.text.toString().toFloat() * arm_rear.text.toString().toFloat()
                    v_moment_zero = v_moment_empty + v_moment_front + v_moment_rear
                    v_weight_zero = weight_empty.text.toString().toFloat() + weight_front.text.toString().toFloat() + weight_rear.text.toString().toFloat()
                } catch (e: Exception) { }

                val v_arm_zero = v_moment_zero / v_weight_zero
                moment_empty.text = v_moment_empty.toString()
                moment_front.text = v_moment_front.toString()
                moment_rear.text = v_moment_rear.toString()
                moment_zero.text = v_moment_zero.toString()
                arm_zero.text = v_arm_zero.toString()
                weight_zero.text = v_weight_zero.toString()
            }
        })
        button_ramp.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                var v_moment_fuel = 0.0F

                try {
                    v_moment_fuel = weight_fuel.text.toString().toFloat() * arm_fuel.text.toString().toFloat()
                    v_moment_ramp = v_moment_zero + v_moment_fuel
                    v_weight_ramp = v_weight_zero + weight_fuel.text.toString().toFloat()
                } catch (e: Exception) { }

                val v_arm_ramp = v_moment_ramp / v_weight_ramp
                moment_fuel.text = v_moment_fuel.toString()
                moment_ramp.text = v_moment_ramp.toString()
                weight_ramp.text = v_weight_ramp.toString()
                arm_ramp.text = v_arm_ramp.toString()
                arm_fuel2.text = arm_fuel.text.toString()
                arm_fuel3.text = arm_fuel.text.toString()
            }
        })
        button_takeoff.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                var v_moment_taxi = 0.0F

                try {
                    v_moment_taxi = weight_taxi.text.toString().toFloat() * arm_fuel.text.toString().toFloat()
                    v_moment_takeoff = v_moment_ramp - v_moment_taxi
                    v_weight_takeoff = v_weight_ramp - weight_taxi.text.toString().toFloat()
                } catch (e: Exception) { }

                val v_arm_takeoff = v_moment_takeoff / v_weight_takeoff
                moment_taxi.text = v_moment_taxi.toString()
                moment_takeoff.text = v_moment_takeoff.toString()
                weight_takeoff.text = v_weight_takeoff.toString()
                arm_takeoff.text = v_arm_takeoff.toString()
            }
        })
        button_landing.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                var v_moment_used = 0.0F
                var v_moment_landing = 0.0F
                var v_weight_landing = 1.0F

                try {
                    v_moment_used = weight_used.text.toString().toFloat() * arm_fuel.text.toString().toFloat()
                    v_moment_landing = v_moment_takeoff - v_moment_used
                    v_weight_landing = v_weight_takeoff - weight_used.text.toString().toFloat()
                } catch (e: Exception) { }

                val v_arm_landing = v_moment_landing / v_weight_landing
                moment_used.text = v_moment_used.toString()
                moment_landing.text = v_moment_landing.toString()
                weight_landing.text = v_weight_landing.toString()
                arm_landing.text = v_arm_landing.toString()
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}