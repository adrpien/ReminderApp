package com.adrpien.reminderapp

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
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

/*
How to:
1. Create MyDatePicker class inheriting Dialog Fragment
    - override onCreateDialog
2. Implement TimePicker  and DatePicker listeners
    - override onDateChanged and on onTimeChanged methods
3. Create AlarmManager
4. Create Pending Intent
5. Set buttons listeners
6. Change minimum API in manifest to use Calendar.Builder
 */

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

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

        /*
        This class provides access to the system alarm services.
        These allow you to schedule your application to be run at some point in the future.
        When an alarm goes off, the Intent that had been registered for it is broadcast by the system,
        automatically starting the target application if it is not already running.
        Registered alarms are retained while the device is asleep
        (and can optionally wake the device up if they go off during that time),
         but will be cleared if it is turned off and rebooted.
         */
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        /*
        A PendingIntent is a token that you give to a foreign application
        which allows the foreign application to use your application's permissions
        to execute a predefined piece of code.
        Panding intent can launch AlarmReceiver (which is BroadcastReceiver)
         */
        alarmIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            Intent(applicationContext, AlarmReceiver::class.java),
            0
        )

        binding.setTimeButton.setOnClickListener {
            // Create MyTimePicker
            val dialog = MyTimePickerDialog()
            // show MyTimePicker
            dialog.show(supportFragmentManager, "time_picker")
        }

        binding.setDateButton.setOnClickListener {
            val dialog = MyDatePickerDialog()
            dialog.show(supportFragmentManager, " date_picker")
        }

        binding.setAlarmButton.setOnClickListener {


            // Create Calendar object
            val date = Calendar.Builder()
                .setDate(year, month, day)
                .setTimeOfDay(hour, minute, 0)
                .build()

            // Set alarm
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.time.time, alarmIntent)
            Toast.makeText(
                applicationContext,
                "Alarm setted on $hour:$minute $day.${month+1}.$year",
                Toast.LENGTH_LONG).show()
        }

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minuteOfHour: Int) {
        this.hour = hourOfDay
        this.minute = minuteOfHour    }


    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        this.year = year
        this.month = monthOfYear
        this.day = dayOfMonth
    }
}