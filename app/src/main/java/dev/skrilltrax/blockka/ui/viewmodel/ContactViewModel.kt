package dev.skrilltrax.blockka.ui.viewmodel

import android.telephony.PhoneNumberUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.skrilltrax.blockka.data.local.LocalContact
import dev.skrilltrax.blockka.data.repo.ContactRepository
import dev.skrilltrax.blockka.data.repo.RecentRepository
import dev.skrilltrax.sqldelight.Contact
import dev.skrilltrax.sqldelight.RecentContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ContactViewModel @ViewModelInject constructor(
  private val contactRepository: ContactRepository,
  private val recentRepository: RecentRepository
) : ViewModel() {

  fun getAllContacts(): Flow<List<Contact>> {
    return contactRepository.getAllContacts()
  }

  fun getAllRecentContacts(): Flow<List<RecentContact>> {
    return recentRepository.getAllRecentContacts()
  }

  fun addContact(number: String) {
    val normalizedNumber = PhoneNumberUtils.normalizeNumber(number)

    viewModelScope.launch {
      contactRepository.addContactLoosely(normalizedNumber)
    }
  }

  fun addContact(number: String, name: String) {
    val normalizedNumber = PhoneNumberUtils.normalizeNumber(number)

    viewModelScope.launch {
      contactRepository.addContactLoosely(normalizedNumber, name)
    }
  }

  fun addContacts(contactList: ArrayList<LocalContact>) {
    viewModelScope.launch {
      contactList.forEach {
        val normalizedNumber = PhoneNumberUtils.normalizeNumber(it.number)

        contactRepository.addContactLoosely(normalizedNumber, it.name, it.imageUri)
      }
    }
  }

  fun deleteContacts(selectedContacts: List<Contact>) {
    viewModelScope.launch {
      selectedContacts.forEach {
        contactRepository.removeContact(it)
      }
    }
  }
}
