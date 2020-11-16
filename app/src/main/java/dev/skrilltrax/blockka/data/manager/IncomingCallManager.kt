package dev.skrilltrax.blockka.data.manager

import android.content.Context
import android.os.Build

interface IncomingCallManager {

    fun rejectCall(number: String): Boolean

    companion object {

        fun getInstance(context: Context): IncomingCallManager {
            return when {
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> IncomingCallManager29Impl(context)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> IncomingCallManager28Impl(context)
                else -> IncomingCallManager21Impl(context)
            }
        }
    }
}
