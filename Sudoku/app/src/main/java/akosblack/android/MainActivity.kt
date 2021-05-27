package akosblack.android

import akosblack.android.database.ResultsDatabase
import akosblack.android.list.ResultsListActivity
import akosblack.android.notification.AlarmReceiver
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.room.Room


class MainActivity() : AppCompatActivity() {

    var mainText: TextView? = null
    var mainBackround: ImageView? = null

    companion object {
        private const val twoDaysInMillis = 172800000
        //private const val oneMinInMillis = 60000

        lateinit var resultsDatabase: ResultsDatabase
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        mainText = findViewById(R.id.mainText)
        mainBackround = findViewById(R.id.backround)

        resultsDatabase = Room.databaseBuilder(
            applicationContext,
            ResultsDatabase::class.java,
            "results_database"
        ).fallbackToDestructiveMigration()
            .build()

        startAnimation()

        val btnHighScore = findViewById<Button>(R.id.btnHighScore)
        btnHighScore.setOnClickListener {
            startActivity(Intent(this, ResultsListActivity::class.java))
        }
        val btnStart = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener {
            startActivity(Intent(this, ChooseDifficulty::class.java))
        }

        val btnAbout = findViewById<Button>(R.id.btnAbout)
        btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

    }

    fun createNotification(): Notification {
        val channelId = "Reminder"
        val activityPendingIntent = PendingIntent.getActivity(
            this, 10, Intent(
                this,MainActivity::class.java
            ),PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Sudoku")
            .setContentText("Már két napja nem játszottál! ")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setVibrate(longArrayOf(1000, 2000, 1000))
            .setContentIntent(activityPendingIntent)
        return builder.build()
    }

    private fun startAnimation(){
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        mainText?.startAnimation(animation)
        mainBackround?.startAnimation(animation)
    }

    override fun onStop(){
        super.onStop()
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("notification", createNotification())
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager: AlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
            (SystemClock.elapsedRealtime() + twoDaysInMillis), pendingIntent)

    }

}
