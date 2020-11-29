package dev.skrilltrax.blockka.data.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telecom.TelecomManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import timber.log.Timber
import javax.inject.Inject

class IncomingCallManager28Impl @Inject constructor(private val context: Context) :
  IncomingCallManager {

  @RequiresApi(Build.VERSION_CODES.P)
  override fun rejectCall(number: String): Boolean {
    Timber.d("Rejecting call: IncomingCallManager28Impl")

    val telecomManager = context.getSystemService<TelecomManager>() ?: return false
    if (ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ANSWER_PHONE_CALLS
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      Timber.d("Permission: ${Manifest.permission.ANSWER_PHONE_CALLS} not granted")
      return false
    }

    return telecomManager.endCall()
  }
}
