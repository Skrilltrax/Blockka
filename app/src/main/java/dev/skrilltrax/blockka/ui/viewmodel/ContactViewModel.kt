package dev.skrilltrax.blockka.ui.viewmodel

import android.telephony.PhoneNumberUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.skrilltrax.blockka.data.repo.BlockkaRepository
import dev.skrilltrax.sqldelight.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ContactViewModel @ViewModelInject constructor(private val repository: BlockkaRepository) : ViewModel() {

  fun getAllContacts(): Flow<List<Contact>> {
    return repository.getAllContacts()
  }

  fun addContact(number: String) {
    val normalizedNumber = PhoneNumberUtils.normalizeNumber(number)

    viewModelScope.launch {
      repository.addContact(normalizedNumber)
    }
  }

  fun addContact(number: String, name: String) {
    val normalizedNumber = PhoneNumberUtils.normalizeNumber(number)

    viewModelScope.launch {
      repository.addContact(normalizedNumber, name)
    }
  }

  fun deleteContacts(selectedContacts: List<Contact>) {
    viewModelScope.launch {
      selectedContacts.forEach {
        repository.removeContact(it)
      }
    }
  }
}
