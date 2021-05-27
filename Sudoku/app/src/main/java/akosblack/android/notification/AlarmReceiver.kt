package akosblack.android.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build


class AlarmReceiver : BroadcastReceiver() {

    companion object{
        private const val NOTIFICATION_ID= "notification_id"
        private const val NOTIFICATION= "notification"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= 26){
            val channel = NotificationChannel(
                "Reminder",
                "Reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        val id = intent.getIntExtra(NOTIFICATION_ID,0)
        notificationManager.notify(id, notification)
    }
}