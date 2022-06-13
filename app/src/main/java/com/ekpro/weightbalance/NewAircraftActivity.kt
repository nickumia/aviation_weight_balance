package com.ekpro.weightbalance

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class NewAircraftActivity : AppCompatActivity() {
    lateinit var name: EditText
    lateinit var empty_weight: EditText
    lateinit var pilot_arm: EditText
    lateinit var row1_arm: EditText
    lateinit var fuel_arm: EditText
    lateinit var baggage_arm: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_aircraft)
        name = findViewById(R.id.airplane_name)
        empty_weight = findViewById(R.id.empty_weight)
        pilot_arm = findViewById(R.id.pilot_arm)
        row1_arm = findViewById(R.id.row1_arm)
        fuel_arm = findViewById(R.id.fuel_arm)
        baggage_arm = findViewById(R.id.baggage_arm)

        val button: Button = findViewById(R.id.button_save)
        button.setOnClickListener {
//            val dao: AircraftDao? = AircraftDao
//            dao.deleteAll()
//            var new_aircraft: Aircraft? = Aircraft(
//                name.text.toString().toInt(),
//                name.text.toString(),
//                empty_weight.text.toString().toInt(),
//                pilot_arm.text.toString().toInt(),
//                row1_arm.text.toString().toInt(),
//                fuel_arm.text.toString().toInt(),
//                baggage_arm.text.toString().toInt(),
//            )
//            dao.insert(word)
            val replyIntent = Intent()
            if (TextUtils.isEmpty(name.text.toString())) {
                setResult(RESULT_CANCELED, replyIntent)
            } else {
                val new_aircraft = Aircraft(System.currentTimeMillis().toInt(), name.text.toString(), empty_weight.text.toString().toFloat() , pilot_arm.text.toString().toFloat() , row1_arm.text.toString().toFloat() , fuel_arm.text.toString().toFloat() , baggage_arm.text.toString().toFloat())
                val dao: AircraftDao = (AircraftDatabase.getDatabase(this)?.aircraftDao()) as AircraftDao
                dao.insertAll(new_aircraft as Aircraft)

                replyIntent.putExtra("aircraft_data", new_aircraft)
                setResult(RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}