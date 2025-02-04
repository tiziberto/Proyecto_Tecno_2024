package com.example.proyecto_tecno

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.proyecto_tecno.LoginActivity.Companion.MY_CHANNEL_ID

class AlarmNotification : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, p1: Intent?) {
        createSimpleNotification(context)
    }

    private fun createSimpleNotification(context: Context) {
        // abrir LoginActivity cuando se toque la noti
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // compatibilidad para API 23+
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        // verificar permiso POST_NOTIFICATIONS
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // constructor de la notificacion
        val notification = NotificationCompat.Builder(context, MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.pin)
            .setContentTitle("Find It")
            .setContentText("Un evento esta por empezar")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Uno de tus eventos favoritos va a comenzar pronto, toca la notificacion para ver mas informacion"))
            .setContentIntent(pendingIntent) // al tocar la notificación
            .build()

        // NotificationManagerCompat para enviarla
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
