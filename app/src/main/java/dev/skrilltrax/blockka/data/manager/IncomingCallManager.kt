package dev.skrilltrax.blockka.data.manager

import dev.skrilltrax.sqldelight.Contact
import dev.skrilltrax.sqldelight.ContactQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IncomingCallManager @Inject constructor(private val contactQueries: ContactQueries) {

    fun isBlocked(number: String) = flow {
        withContext(Dispatchers.IO) {
            val result = contactQueries.isBlocked(number).executeAsOneOrNull()
            if (result is Contact) {
                emit(true)
            } else {
                emit(false)
            }
        }
    }

}
