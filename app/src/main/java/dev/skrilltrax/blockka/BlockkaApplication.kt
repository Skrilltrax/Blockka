package dev.skrilltrax.blockka

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.getSystemService
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BlockkaApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    createNotificationChannel()
    Timber.plant(Timber.DebugTree())
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val serviceChannel = NotificationChannel(
        CHANNEL_ID,
        getString(R.string.app_name),
        NotificationManager.IMPORTANCE_LOW
      )
      val manager = getSystemService<NotificationManager>()
      if (manager != null) {
        manager.createNotificationChannel(serviceChannel)
      } else {
        Timber.d("Failed to create notification channel")
      }
    }
  }

  companion object {
    private const val CHANNEL_ID = "BlockkaNotification"
  }
}
