package com.adrpien.reminderapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class AlarmReceiver: BroadcastReceiver() {

    private val CHANNEL_ID = "ALARM_CHANNEL"
    private val CHANNEL_NAME = "CHANNEL_NAME"

    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelNotification: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("AlarmApp")
            .setContentText("Alarm")
            .setSmallIcon(R.drawable.alarm_icon)

        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             notificationManager.createNotificationChannel(channel)
             notificationManager.notify(1, channelNotification.build())
         }



    }
}