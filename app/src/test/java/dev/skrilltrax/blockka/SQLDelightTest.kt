package dev.skrilltrax.blockka

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.skrilltrax.blockka.data.local.LocalContact
import dev.skrilltrax.blockka.model.BlockkaDatabase
import dev.skrilltrax.sqldelight.ContactQueries
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test

class SQLDelightTest {

  @Test
  fun testCount() = runBlocking {
    val data = createTestData(3)
    data.forEach { contactQueries.insertContactByNumber(it.number) }
    val count = contactQueries.selectCount().executeAsOne()

    assertEquals(count, 3)
  }

  companion object {
    private lateinit var contactQueries: ContactQueries

    @BeforeClass
    @JvmStatic
    fun setup() {
      val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
      BlockkaDatabase.Schema.create(driver)
      val database = BlockkaDatabase(driver)

      contactQueries = database.contactQueries
    }

    private fun createTestData(count: Int): ArrayList<LocalContact> {
      val contacts = arrayListOf<LocalContact>()

      for (i in 0 until count) {
        contacts.add(LocalContact("user_$i", "999999999$i"))
      }

      return contacts
    }
  }
}
