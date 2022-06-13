package com.ekpro.weightbalance.ui.aircraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekpro.weightbalance.Aircraft
import com.ekpro.weightbalance.AircraftDao
import com.ekpro.weightbalance.AircraftDatabase
import com.ekpro.weightbalance.AircraftListAdapter
import com.ekpro.weightbalance.databinding.FragmentAircraftsBinding


class AircraftFragment : Fragment() {

    private var _binding: FragmentAircraftsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val aircraftViewModel by activityViewModels<AircraftViewModel>()
//            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[AircraftViewModel::class.java]

        val dao: AircraftDao = (activity?.let { AircraftDatabase.getDatabase(it.applicationContext)?.aircraftDao() }) as AircraftDao
        val allAircrafts: LiveData<List<Aircraft>> = dao.getAll()

        _binding = FragmentAircraftsBinding.inflate(inflater, container, false)
        val root: View = binding.root

      // val textView: TextView = binding.text
        val rView: RecyclerView = binding.recyclerview
        val adapter = AircraftListAdapter(AircraftListAdapter.AircraftDiff())
        rView.adapter = adapter
        allAircrafts.observe(viewLifecycleOwner) { aircrafts ->
            // Update the cached copy of the words in the adapter.
            adapter.submitList(aircrafts)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}