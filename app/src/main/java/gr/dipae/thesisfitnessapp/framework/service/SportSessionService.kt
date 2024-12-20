package gr.dipae.thesisfitnessapp.framework.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.data.sport.broadcast.StopWatchBroadcast
import gr.dipae.thesisfitnessapp.framework.sport.location.SportSessionLocationProvider
import kotlinx.coroutines.DelicateCoroutinesApi
import timber.log.Timber
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.milliseconds

@DelicateCoroutinesApi
@AndroidEntryPoint
class SportSessionService : Service() {

    @Inject
    lateinit var stopWatchBroadcast: StopWatchBroadcast

    @Inject
    lateinit var notification: NotificationCompat.Builder

    @Inject
    lateinit var sportSessionLocationProvider: SportSessionLocationProvider

    private lateinit var notificationManager: NotificationManager

    private var duration: Duration = ZERO
    private lateinit var timer: Timer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleIntent(intent)
        Timber.tag(SportSessionService::class.simpleName.toString()).e("StopWatchService - onStartCommand")
        return START_STICKY
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null || intent.action == null) return

        createNotificationChannel()
        updateStopWatchNotification()

        when (intent.action) {
            STOP_WATCH_ACTION_START -> {
                sportSessionLocationProvider.startUserLocationUpdates(5000)
                startTimer()
            }

            STOP_WATCH_ACTION_PAUSE -> {
                sportSessionLocationProvider.stopTracking()
                pauseTimer()
            }

            STOP_WATCH_ACTION_STOP -> {
                clearService()
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                STOP_WATCH_NOTIFICATION_CHANNEL_ID,
                getString(R.string.sport_session_stop_watch_notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            )
            channel.enableVibration(false)
            channel.vibrationPattern = longArrayOf()
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startTimer() {
        timer = fixedRateTimer(period = 10L) {
            duration = duration.plus(10.milliseconds)
            updateSportSessionStopWatch()
            if (duration.inWholeMilliseconds.mod(1000) == 0) {
                updateStopWatchNotification()
            }
        }
    }

    private fun updateSportSessionStopWatch() {
        stopWatchBroadcast.refreshStopWatchMillisPassed(duration.inWholeMilliseconds)
    }

    private fun pauseTimer() {
        if (this::timer.isInitialized) {
            timer.cancel()
            timer.purge()
            updateStopWatchNotification()
        }
    }

    private fun updateStopWatchNotification() {
        notification.setContentText(convertDurationToStopWatchTimerString(duration.inWholeMilliseconds))
        notificationManager.notify(1, notification.build())
    }

    private fun convertDurationToStopWatchTimerString(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = ((millis / (1000 * 60)) % 60)
        val hours = ((millis / (1000 * 60 * 60)) % 24)

        return "${String.format("%02d", hours)}:${String.format("%02d", minutes)}:${String.format("%02d", seconds)}"
    }

    private fun removeStopWatchNotification() {
        pauseTimer()
        notificationManager.cancel(1)
    }

    private fun clearService() {
        removeStopWatchNotification()
        stopWatchBroadcast.clear()
        sportSessionLocationProvider.stopTracking()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        clearService()
        Timber.tag(SportSessionService::class.simpleName.toString()).e("StopWatchService - onDestroy")
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        clearService()
        Timber.tag(SportSessionService::class.simpleName.toString()).e("StopWatchService - onTaskRemoved: ${rootIntent.toString()}")
        super.onTaskRemoved(rootIntent)
    }

    companion object {
        const val STOP_WATCH_NOTIFICATION_CHANNEL_ID = "thesis_fitness_app_01"
        const val STOP_WATCH_ACTION_START = "action_start"
        const val STOP_WATCH_ACTION_PAUSE = "action_pause"
        const val STOP_WATCH_ACTION_STOP = "action_stop"
    }
}