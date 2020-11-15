package dev.skrilltrax.blockka.data.manager

import android.content.Context
import android.telephony.TelephonyManager
import androidx.core.content.getSystemService
import com.android.internal.telephony.ITelephony
import timber.log.Timber
import java.lang.reflect.Method
import javax.inject.Inject

class IncomingCallManager21Impl @Inject constructor(private val context: Context) :
    IncomingCallManager {

    override fun rejectCall(number: String): Boolean {
        Timber.d("Rejecting call: IncomingCallManager21Impl")

        val telephonyManager = context.getSystemService<TelephonyManager>() ?: return false
        val clazz = Class.forName(telephonyManager.javaClass.name)
        val method: Method = clazz.getDeclaredMethod("getITelephony")
        method.isAccessible = true
        val telephonyService: ITelephony = method.invoke(telephonyManager) as ITelephony

        return telephonyService.endCall()
    }

}
