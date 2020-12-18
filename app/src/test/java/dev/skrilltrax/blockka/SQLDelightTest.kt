package dev.skrilltrax.blockka

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.skrilltrax.blockka.data.local.LocalContact
import dev.skrilltrax.blockka.model.BlockkaDatabase
import dev.skrilltrax.sqldelight.ContactQueries
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class SQLDelightTest {

  @Test
  fun `get item count`() = runBlocking {
    val data = createTestData(3)
    data.forEach { contactQueries.insertContactByNumber(it.number) }
    val count = contactQueries.selectCount().executeAsOne()

    assertEquals(count, 3)
  }

  @Test
  fun `select all items`() = runBlocking {
    val data = createTestData(3)
    data.forEach { contactQueries.insertContactByNumberAndName(it.number, it.name) }
    val output = contactQueries.selectAllContacts().executeAsList()

    assertEquals(3, output.size)
    assertEquals("user_0", output[0].name)
    assertEquals("9999999990", output[0].number)
    assertEquals("user_1", output[1].name)
    assertEquals("9999999991", output[1].number)
    assertEquals("user_2", output[2].name)
    assertEquals("9999999992", output[2].number)
  }

  @Test
  fun `select item by id`() = runBlocking {
    val data = createTestData(1)
    data.forEach { contactQueries.insertContactByNumber(it.number) }
    val dbContacts = contactQueries.selectAllContacts().executeAsList()
    assertEquals(1, dbContacts.size)

    val contact = dbContacts[0]
    val output = contactQueries.selectContact(contact.id).executeAsOne()
    assertEquals(contact.id, output.id)
    assertEquals(contact.name, output.name)
    assertEquals(contact.number, output.number)
  }

  @Test
  fun `is contact blocked`() {
    val data = createTestData(1)
    data.forEach { contactQueries.insertContactByNumber(it.number) }
    val dbContacts = contactQueries.selectAllContacts().executeAsList()
    assertEquals(1, dbContacts.size)

    val contact = dbContacts[0]
    val contactList = contactQueries.isBlocked("9999999990").executeAsList()

    assertEquals(contactList.size, 1)
    assertEquals(contactList[0].number, contact.number)
  }

  @Test
  fun `increment call count`() {
    val data = createTestData(1)
    data.forEach { contactQueries.insertContactByNumber(it.number) }
    val dbContacts = contactQueries.selectAllContacts().executeAsList()
    assertEquals(1, dbContacts.size)

    val contact = dbContacts[0]
    val contactList = contactQueries.isBlocked("9999999990").executeAsList()

    assertEquals(contactList.size, 1)
    assertEquals(contactList[0].number, contact.number)
    assertEquals(contactList[0].name, contact.name)
    assertEquals(contactList[0].call_count, 0)

    contactQueries.incrementCallCount(contactList[0].id)
    val updatedContact = contactQueries.selectContact(contact.id).executeAsOne()
    assertEquals(updatedContact.call_count, 1)
  }

  @Before
  fun setup() {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    BlockkaDatabase.Schema.create(driver)
    val database = BlockkaDatabase(driver)

    contactQueries = database.contactQueries
  }

  companion object {
    private lateinit var contactQueries: ContactQueries

    private fun createTestData(count: Int): ArrayList<LocalContact> {
      val contacts = arrayListOf<LocalContact>()

      for (i in 0 until count) {
        contacts.add(LocalContact("user_$i", "999999999$i"))
      }

      return contacts
    }
  }
}
