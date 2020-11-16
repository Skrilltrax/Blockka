package dev.skrilltrax.blockka.data.repo

import android.telephony.PhoneNumberUtils
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.skrilltrax.sqldelight.Contact
import dev.skrilltrax.sqldelight.ContactQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BlockkaRepository @Inject constructor(private val contactQueries: ContactQueries) {

  suspend fun isContactBlocked(number: String): Boolean = withContext(Dispatchers.IO) {
    return@withContext isContactBlockedStrict(number)
  }

  suspend fun addContact(contact: Contact) = withContext(Dispatchers.IO) {
    contactQueries.insertOrReplaceContact(contact)
  }

  suspend fun addContact(number: String) = withContext(Dispatchers.IO) {
    contactQueries.insertContactByNumber(number)
  }

  suspend fun addContact(number: String, name: String) = withContext(Dispatchers.IO) {
    contactQueries.insertContactByNumberAndName(number, name)
  }

  fun getAllContacts(): Flow<List<Contact>> {
    return contactQueries.selectAllContacts().asFlow().mapToList()
  }

  suspend fun incrementCallCount(contact: Contact) = withContext(Dispatchers.IO) {
    if (isContactBlocked(contact.number)) {
      contactQueries.incrementCallCount(contact.id)
    } else {
      addContact(contact)
    }
  }

  suspend fun removeContact(contact: Contact) = withContext(Dispatchers.IO) {
    contactQueries.deleteContact(contact.id)
  }

  private suspend fun isContactBlockedStrict(number: String): Boolean =
    withContext(Dispatchers.IO) {
      val contact = contactQueries.isBlocked(number).executeAsOneOrNull()
      return@withContext contact != null
    }

  private suspend fun isContactBlockedLoose(number: String) = withContext(Dispatchers.IO) {
    val allContacts = contactQueries.selectAllContacts().executeAsList()

    allContacts.forEach {
      if (PhoneNumberUtils.compare(it.number, number)) {
        return@withContext true
      }
    }

    return@withContext false
  }
}
