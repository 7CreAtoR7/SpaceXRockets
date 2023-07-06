package ru.ilya.spacexrockets.data.remote.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.ilya.spacexrockets.R
import ru.ilya.spacexrockets.data.local.AppDatabase
import ru.ilya.spacexrockets.data.remote.SpaceXApi
import ru.ilya.spacexrockets.presentation.MainActivity
import ru.ilya.spacexrockets.util.Constants.ERROR_TAG


class CheckNewRocketsWorker(
    context: Context,
    params: WorkerParameters,
    private val appDb: AppDatabase,
    private val api: SpaceXApi
) : CoroutineWorker(context, params) {

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(id: Int) {
        Log.d(CHECK_WORK_MANAGER_TAG, "showNotification")
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("SpaceX сделала космическую ракету!")
            .setContentText("Посмотри скорее в приложении по клику!")
            .setSmallIcon(R.drawable.success_launch)
            .setChannelId(CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, notification)
    }

    override suspend fun doWork(): Result {
        val hasNewData = checkForNewData()
        Log.d(CHECK_WORK_MANAGER_TAG, "hasNewData = $hasNewData")
        if (hasNewData) {
            showNotification(1)
        }
        return Result.success()
    }

    private suspend fun checkForNewData(): Boolean {
        val localDataSize = getLocalDataSizeFromDb()
        val remoteDataSize = getRemoteDataSizeFromServer()
        Log.d(
            CHECK_WORK_MANAGER_TAG,
            "localDataSize = $localDataSize, remoteDataSize=$remoteDataSize"
        )
        return remoteDataSize >= localDataSize
    }

    private fun getLocalDataSizeFromDb(): Int {
        try {
            val localRocketsList = appDb.rocketsDao().getLocalRockets()
            return localRocketsList.size
        } catch (e: Exception) {
            Log.d(ERROR_TAG, "CheckNewRocketsWorker getLocalDataSizeFromDb error: $e")
        }
        return DEFAULT_VALUE
    }

    private suspend fun getRemoteDataSizeFromServer(): Int {
        try {
            val remoteRocketsList = api.getRockets()
            return remoteRocketsList.size
        } catch (e: Exception) {
            Log.d(ERROR_TAG, "CheckNewRocketsWorker getActualDataSizeFromServer error: $e")
        }
        return DEFAULT_VALUE
    }


    companion object {

        private const val CHECK_WORK_MANAGER_TAG = "CHECKWORKMANAGER"

        const val NOTIFICATION_ID = "notification_id"
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "Проверка на новые ракеты"

        private const val DEFAULT_VALUE = 0
    }
}