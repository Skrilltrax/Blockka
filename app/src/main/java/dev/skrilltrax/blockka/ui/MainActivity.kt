package dev.skrilltrax.blockka.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.skrilltrax.blockka.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

  private val requestPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
      permissions.entries.forEach { entry ->
        Timber.d("Permission: %s Granted : %s", entry.key, entry.value)
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onResume() {
    super.onResume()
    requestPermissions()
  }

  private fun requestPermissions() {
    val permissions =
      arrayListOf(Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      permissions.add(Manifest.permission.ANSWER_PHONE_CALLS)
    }

    val permissionsArray = permissions.toTypedArray()

    permissions.forEach {
      Timber.d("Permission : %s", it)

      when (ContextCompat.checkSelfPermission(this, it)) {
        PackageManager.PERMISSION_GRANTED -> {
          // We have the permission, no need to do anything
          Timber.d("Permission Granted: %s", it)
        }

        PackageManager.PERMISSION_DENIED -> {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(it)) {
            // Show permission rationale
            Timber.d("Show permission rationale: %s", it)
            Toast.makeText(this, "Please enable all permissions in settings", Toast.LENGTH_LONG)
              .show()
          } else {
            // Just ask for permission at this point
            Timber.d("Don't show permission rationale: %s", it)
            requestPermissionLauncher.launch(permissionsArray)
          }
        }

        else -> {
          Timber.d("Why is this here: %s", it)
          requestPermissionLauncher.launch(permissionsArray)
        }
      }
    }
  }
}
