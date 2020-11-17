package dev.skrilltrax.blockka.data.listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import dagger.hilt.android.AndroidEntryPoint
import dev.skrilltrax.blockka.data.manager.IncomingCallManager
import dev.skrilltrax.blockka.data.repo.ContactRepository
import dev.skrilltrax.blockka.data.repo.RecentRepository
import dev.skrilltrax.sqldelight.Contact
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class IncomingCallReceiver : BroadcastReceiver() {
    @Inject lateinit var contactRepository: ContactRepository
    @Inject lateinit var recentRepository: RecentRepository
    @Inject lateinit var incomingCallManager: IncomingCallManager

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)
    private var previousState = TelephonyManager.CALL_STATE_IDLE

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // We are gonna use the CallScreeningApi in this case
            // TODO: Return here once we implement CallScreeningApi
            // return
        }

        // Validate the incoming intent if it has all the data we need to reject a call.
        if (!isIncomingIntentValid(context, intent)) return

        // We have already validated the intent
        val bundle = intent!!.extras as Bundle
        val extraState = bundle.getString(TelephonyManager.EXTRA_STATE)
        val number = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER) ?: ""

        if (number.isEmpty()) return
        if (extraState.isNullOrEmpty()) return

        if (!isIncomingCall(extraState)) return

        val normalizedNumber = PhoneNumberUtils.normalizeNumber(number)

        coroutineScope.launch {
            val contact = shouldRejectCall(normalizedNumber)
            if (contact is Contact) {
                rejectCall(contact)
            }
        }

    }

    private fun isIncomingIntentValid(context: Context?, intent: Intent?): Boolean {
        if (context !is Context) {
            Timber.d("Context is null")
            return false
        }

        if (intent !is Intent) {
            Timber.d("Intent is null")
            return false
        }

        if (intent.action != TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            return false
        }

        val bundle = intent.extras

        if (bundle !is Bundle) {
            Timber.d("bundle is null")
            return false
        }

        if (!intent.hasExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)) {
            return false
        }

        return true
    }

    private fun isIncomingCall(extraState: String): Boolean {
        val currentState = when (extraState) {
            TelephonyManager.EXTRA_STATE_IDLE -> TelephonyManager.CALL_STATE_IDLE
            TelephonyManager.EXTRA_STATE_OFFHOOK -> TelephonyManager.CALL_STATE_OFFHOOK
            TelephonyManager.EXTRA_STATE_RINGING -> TelephonyManager.CALL_STATE_RINGING
            else -> TelephonyManager.CALL_STATE_IDLE
        }

        previousState = TelephonyManager.CALL_STATE_IDLE

        return currentState == TelephonyManager.CALL_STATE_RINGING &&
                previousState == TelephonyManager.CALL_STATE_IDLE
    }

    private suspend fun rejectCall(contact: Contact) {
        withContext(Dispatchers.Main) {
            val normalizedNumber = PhoneNumberUtils.normalizeNumber(contact.number)
            contactRepository.incrementCallCount(contact)
            if (contact.name != null) {
                recentRepository.addRecent(normalizedNumber, contact.name)
            } else {
                recentRepository.addRecent(normalizedNumber)
            }
            incomingCallManager.rejectCall(normalizedNumber)
        }
    }

    private suspend fun shouldRejectCall(number: String): Contact? {
        return contactRepository.getContactByNumber(number)
    }
}
