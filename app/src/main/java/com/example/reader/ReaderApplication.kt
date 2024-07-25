package com.example.reader

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.unit.Constraints
import com.example.reader.util.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReaderApplication: Application() {
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onCreate() {
		super.onCreate()
		val notificationChannel = NotificationChannel(
			Constants.NOTIFICATION_CHANNEL_ID,
			Constants.NOTIFICATION_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)
		val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.createNotificationChannel(notificationChannel)
	}
}