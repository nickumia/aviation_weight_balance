package com.ekpro.weightbalance

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class AircraftListAdapter(diffCallback: DiffUtil.ItemCallback<Aircraft>) :
    ListAdapter<Aircraft, AircraftViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AircraftViewHolder {
        return AircraftViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AircraftViewHolder, position: Int) {
        val current: Aircraft = getItem(position)
        holder.bind(
            current.makemodel,
            current.weight_empty,
            current.weight_pilot,
            current.weight_row1,
            current.weight_fuel,
            current.weight_baggage
        )
    }

    class AircraftDiff : DiffUtil.ItemCallback<Aircraft>() {
        override fun areItemsTheSame(oldItem: Aircraft, newItem: Aircraft): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Aircraft, newItem: Aircraft): Boolean {
            return oldItem.makemodel.equals(newItem.makemodel)
        }
    }
}

class AircraftViewHolder private constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val nameView: TextView
    private val pilotView: TextView
    private val row1View: TextView
    private val fuelView: TextView
    private val baggageView: TextView
    private val emptyWeightView: TextView
    fun bind(makemodel: String?,
             weight_empty: Float?,
             arm_pilot: Float?,
             arm_row1: Float?,
             arm_fuel: Float?,
             arm_baggage: Float?) {
        nameView.text = makemodel
        pilotView.text = arm_pilot.toString()
        row1View.text = arm_row1.toString()
        fuelView.text = arm_fuel.toString()
        baggageView.text = arm_baggage.toString()
        emptyWeightView.text = weight_empty.toString()
    }

    companion object {
        fun create(parent: ViewGroup): AircraftViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
            return AircraftViewHolder(view)
        }
    }

    init {
        nameView = itemView.findViewById(R.id.airplane_name)
        pilotView = itemView.findViewById(R.id.pilot_arm)
        row1View = itemView.findViewById(R.id.row1_arm)
        fuelView = itemView.findViewById(R.id.fuel_arm)
        baggageView = itemView.findViewById(R.id.baggage_arm)
        emptyWeightView = itemView.findViewById(R.id.empty_weight)
    }
}

//internal class AircraftRepository(application: Context) {
//    private val mAircraftDao: AircraftDao = TODO()
//    private val mAllAircrafts: LiveData<List<Aircraft>>
//
//    // Room executes all queries on a separate thread.
//    // Observed LiveData will notify the observer when the data has changed.
//    val allAircrafts: LiveData<List<Aircraft>>
//        get() = mAllAircrafts
//
//    // Note that in order to unit test the WordRepository, you have to remove the Application
//    // dependency. This adds complexity and much more code, and this sample is not about testing.
//    // See the BasicSample in the android-architecture-components repository at
//    // https://github.com/googlesamples
//    init {
//        val db: AircraftDatabase? = AircraftDatabase.getDatabase(application)
//        if (db != null) {
//            mAircraftDao = db.aircraftDao()
//        }
//        mAllAircrafts = mAircraftDao.getAll()
//    }
//}