package dev.skrilltrax.blockka.data.repo

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.skrilltrax.sqldelight.RecentContact
import dev.skrilltrax.sqldelight.RecentContactQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RecentRepository @Inject constructor(val recentContactQueries: RecentContactQueries) {

  suspend fun addRecent(number: String) = withContext(Dispatchers.IO) {
    val currDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    val currTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

    val currentTimeStamp = "$currDate ($currTime)"
    recentContactQueries.insertContactByNumber(number, currentTimeStamp)
  }

  fun getAllRecentContacts(): Flow<List<RecentContact>> {
    return recentContactQueries.selectAll().asFlow().mapToList()
  }

  fun getAllRecentContactByNumber(number: String): Flow<List<RecentContact>> {
    return recentContactQueries.selectByNumber(number).asFlow().mapToList()
  }
}
