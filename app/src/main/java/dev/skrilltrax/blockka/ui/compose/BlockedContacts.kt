package dev.skrilltrax.blockka.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.skrilltrax.blockka.R
import dev.skrilltrax.blockka.ui.viewmodel.ContactViewModel
import dev.skrilltrax.sqldelight.Contact

@Composable
fun BlockedContactsList() {
  val contactViewModel by viewModel<ContactViewModel>(HiltViewModelFactory(LocalContext.current ))
  val blockedContactList by contactViewModel.getAllContacts().collectAsState(initial = emptyList())

  LazyColumn {
    items(blockedContactList) { contact ->
      BlockedContact(contact = contact)
    }
  }
}

@Composable
fun BlockedContact(contact: Contact) {
  val image: Any = if (contact.image_uri.isNullOrEmpty()) {
    R.drawable.ic_account_circle_24
  } else {
    contact.image_uri
  }

  Row(
    modifier = Modifier.height(86.dp).fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    CoilImage(
      data = image,
      modifier = Modifier.padding(start = 8.dp, end = 8.dp).size(64.dp),
      contentDescription = "Contact Image"
    )

    Column(horizontalAlignment = Alignment.Start) {
      if (!contact.name.isNullOrEmpty()) {
        Text(contact.name, modifier = Modifier.padding(bottom = 8.dp))
      }
      Text(contact.number)
    }
  }
}

@Preview(
  showBackground = true,
  backgroundColor = 0xFFF,
  showSystemUi = true,
  device = Devices.PIXEL_4
)
@Composable
fun PreviewBlockedContact() {
  val contact = Contact(100, "+91 9876543210", name = "John Doe", call_count = 0, image_uri = null)
  BlockedContact(contact = contact)
}
