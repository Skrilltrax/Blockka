package dev.skrilltrax.blockka.data.repo

import android.telephony.PhoneNumberUtils
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.skrilltrax.sqldelight.Contact
import dev.skrilltrax.sqldelight.ContactQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactQueries: ContactQueries) {

  private suspend fun isContactBlocked(number: String): Boolean = withContext(Dispatchers.IO) {
    return@withContext isContactBlockedLoosely(number)
  }

  private suspend fun addContact(number: String) = withContext(Dispatchers.IO) {
    contactQueries.insertContactByNumber(number)
  }

  private suspend fun addContact(number: String, name: String) = withContext(Dispatchers.IO) {
    contactQueries.insertContactByNumberName(number, name)
  }

  suspend fun addContactLoosely(number: String) = withContext(Dispatchers.IO) {
    val allContacts = contactQueries.selectAllContacts().executeAsList()

    allContacts.forEach {
      if (PhoneNumberUtils.compare(it.number, number)) {
        return@withContext
      }
    }

    contactQueries.insertContactByNumber(number)
  }

  suspend fun addContactLoosely(number: String, name: String, imageUri: String? = null) = withContext(Dispatchers.IO) {
    val allContacts = contactQueries.selectAllContacts().executeAsList()
    var shouldAdd = true

    allContacts.forEach {
      if (PhoneNumberUtils.compare(it.number, number)) {
        Timber.d(it.number)
        shouldAdd = false
      }
    }

    if (shouldAdd) contactQueries.insertContactByNumberNameImage(number, name, imageUri)
  }

  fun getAllContacts(): Flow<List<Contact>> {
    return contactQueries.selectAllContacts().asFlow().mapToList()
  }

  suspend fun getContactByNumber(number: String) = withContext(Dispatchers.IO) {
    val allContacts = contactQueries.selectAllContacts().executeAsList()

    allContacts.forEach {
      if (PhoneNumberUtils.compare(it.number, number)) {
        return@withContext it
      }
    }

    return@withContext null
  }

  suspend fun incrementCallCount(contact: Contact) = withContext(Dispatchers.IO) {
    if (isContactBlocked(contact.number)) {
      contactQueries.incrementCallCount(contact.id)
    } else {
      if (contact.name == null) {
        addContact(contact.number)
      } else {
        addContact(contact.name, contact.number)
      }
    }
  }

  suspend fun incrementCallCount(number: String) = withContext(Dispatchers.IO) {
    val allContacts = contactQueries.selectAllContacts().executeAsList()

    allContacts.forEach {
      if (PhoneNumberUtils.compare(it.number, number)) {
        incrementCallCount(it)
      }
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

  private suspend fun isContactBlockedLoosely(number: String) = withContext(Dispatchers.IO) {
    val allContacts = contactQueries.selectAllContacts().executeAsList()

    allContacts.forEach {
      if (PhoneNumberUtils.compare(it.number, number)) {
        return@withContext true
      }
    }

    return@withContext false
  }
}
