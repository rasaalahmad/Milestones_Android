package com.example.milestone2.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.milestone2.ContactsNavigationDrawerActivity
import com.example.milestone2.R
import java.util.*

class NotificationForegroundService:Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, ContactsNavigationDrawerActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, "my_contacts_channel_id")
            .setContentTitle("Foreground Service")
            .setContentText("Contacts App is Running...")
            .setSmallIcon(R.drawable.user_icon_64px)
            .setContentIntent(pendingIntent)
            .build()

        //startForeground(1, notification)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 17)
        calendar.set(Calendar.MINUTE, 22)
        calendar.set(Calendar.SECOND, 0)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                // Do your notification here
                startForeground(Calendar.SECOND, notification)
            }
        }, calendar.time)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}