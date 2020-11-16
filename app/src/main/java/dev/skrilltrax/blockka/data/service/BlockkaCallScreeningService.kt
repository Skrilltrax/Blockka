package dev.skrilltrax.blockka.data.service

import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class BlockkaCallScreeningService: CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {
    }
}
