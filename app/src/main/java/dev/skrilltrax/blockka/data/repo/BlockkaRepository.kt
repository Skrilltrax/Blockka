package dev.skrilltrax.blockka.data.repo

import dev.skrilltrax.sqldelight.ContactQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BlockkaRepository @Inject constructor(private val contactQueries: ContactQueries) {

  suspend fun isContactBlocked(number: String): Boolean = withContext(Dispatchers.IO) {
    val contact = contactQueries.isBlocked(number).executeAsOneOrNull()
    return@withContext contact != null
  }
}
