package com.example.examenalumnobreakdown.ui.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHandler(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val notificationChannelID = "breakdown_channel_id"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelID,
                "Averías",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de creación de averías"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, text: String) {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(title)
            .setContentText(text)
            .build()

        notificationManager.notify(1, notification)
    }
}