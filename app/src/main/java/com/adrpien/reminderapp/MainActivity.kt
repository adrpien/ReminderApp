package com.adrpien.reminderapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.adrpien.reminderapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener {

    private lateinit var binding: ActivityMainBinding

    private var hour = 0
    private var minute = 0
    private  var day = 0
    private var month = 0
    private var year = 0

    lateinit var alarmManager: AlarmManager
    lateinit var alarmIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            Intent(applicationContext, AlarmReceiver::class.java),
            0
        )

        binding.setTimeButton.setOnClickListener {
            val dialog = MyTimePickerDialog()
            dialog.show(supportFragmentManager, "time_picker")
        }

        binding.setDateButton.setOnClickListener {
            val dialog = MyDatePickerDialog()
            dialog.show(supportFragmentManager, " date_picker")
        }

        binding.setAlarmButton.setOnClickListener {

            val date = Calendar.Builder()
                .setDate(year, month, day)
                .setTimeOfDay(hour, minute, 0)
                .build()

            alarmManager.set(AlarmManager.RTC_WAKEUP, date.timeInMillis, alarmIntent)
            Toast.makeText(applicationContext, "Alarm setted", Toast.LENGTH_LONG)
        }

    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minuteOfHour: Int) {
        this.hour = hourOfDay
        this.minute = minuteOfHour
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        this.year = year
        this.month = monthOfYear
        this.day = dayOfMonth
    }
}