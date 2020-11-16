package dev.skrilltrax.blockka.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.skrilltrax.blockka.R
import dev.skrilltrax.blockka.databinding.ActivityMainBinding
import dev.skrilltrax.blockka.ui.fragment.AddContactFragment
import dev.skrilltrax.blockka.ui.fragment.EnterNumberFragment
import dev.skrilltrax.blockka.ui.fragment.NavigationSheetFragment
import dev.skrilltrax.blockka.ui.viewmodel.ContactViewModel
import dev.skrilltrax.blockka.utils.viewBinding
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private val requestPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
      permissions.entries.forEach { entry ->
        Timber.d("Permission: %s Granted : %s", entry.key, entry.value)
      }
    }

  private val contactLauncher = registerForActivityResult(ActivityResultContracts.PickContact()) {
    if (it == null) {
      Toast.makeText(this, "No contact selected", Toast.LENGTH_LONG).show()
      return@registerForActivityResult
    }

    val cursor = contentResolver.query(it, null, null, null, null)

    try {
      if (cursor != null && cursor.moveToFirst()) {
        val hasPhone =
          cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

        if (hasPhone.trim() != "1") return@registerForActivityResult

        val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
        val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

        val phones = contentResolver.query(
          Phone.CONTENT_URI,
          null,
          Phone.CONTACT_ID + " = " + contactId,
          null,
          null
        )

        if (phones != null && phones.moveToFirst()) {
          do {
            val number = phones.getString(phones.getColumnIndex(Phone.NUMBER))

            viewModel.addContact(number, name)
          } while (phones.moveToNext())
        }
      }
    } catch (e: Exception) {
      Timber.e(e)
    }
  }

  private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
  private val viewModel: ContactViewModel by viewModels()
  private lateinit var navHostFragment: NavHostFragment
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setSupportActionBar(binding.bottomAppBar)

    navHostFragment =
      supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController

    setupBottomSheet()

    with(binding) {
      fab.setOnClickListener {
        val addContactFragment = AddContactFragment()
        addContactFragment.show(supportFragmentManager, addContactFragment.tag)
      }
    }
  }

  override fun onResume() {
    super.onResume()
    requestPermissions()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.bottom_app_bar, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    Timber.d("%s", item.itemId)
    when (item.itemId) {
      android.R.id.home -> {
        val bottomNavDrawerFragment = NavigationSheetFragment(ListType.ALL_CONTACTS)
        bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
      }
    }
    return true
  }

  private fun setupBottomSheet() {
    supportFragmentManager.setFragmentResultListener(
      NavigationSheetFragment.REQUEST_LIST_TYPE,
      this
    ) { _, bundle ->
      when (bundle.getString(NavigationSheetFragment.REQUEST_LIST)) {
        NavigationSheetFragment.REQUEST_ALL_CONTACTS -> {
          Toast.makeText(this, "all", Toast.LENGTH_LONG).show()
        }
        NavigationSheetFragment.REQUEST_RECENT_CONTACTS -> {
          Toast.makeText(this, "recent", Toast.LENGTH_LONG).show()
        }
      }
    }

    supportFragmentManager.setFragmentResultListener(
      AddContactFragment.REQUEST_ADD_CONTACTS,
      this
    ) { _, bundle ->
      when (bundle.getString(AddContactFragment.REQUEST_ADD)) {
        AddContactFragment.REQUEST_ADD_MANUAL -> {
          val enterNumberFragment = EnterNumberFragment()
          enterNumberFragment.show(supportFragmentManager, enterNumberFragment.tag)
        }
        AddContactFragment.REQUEST_ADD_CONTACTS -> {
          contactLauncher.launch()
        }
      }
    }
  }

  private fun requestPermissions() {
    val permissions =
      arrayListOf(Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      permissions.add(Manifest.permission.ANSWER_PHONE_CALLS)
    }

    val permissionsArray = permissions.toTypedArray()

    permissions.forEach {
      Timber.d("Permission : %s", it)

      when (ContextCompat.checkSelfPermission(this, it)) {
        PackageManager.PERMISSION_GRANTED -> {
          // We have the permission, no need to do anything
          Timber.d("Permission Granted: %s", it)
        }

        PackageManager.PERMISSION_DENIED -> {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
              it
            )
          ) {
            // Show permission rationale
            Timber.d("Show permission rationale: %s", it)
            Toast.makeText(this, "Please enable all permissions in settings", Toast.LENGTH_LONG)
              .show()
          } else {
            // Just ask for permission at this point
            Timber.d("Don't show permission rationale: %s", it)
            requestPermissionLauncher.launch(permissionsArray)
          }
        }

        else -> {
          Timber.d("Why is this here: %s", it)
          requestPermissionLauncher.launch(permissionsArray)
        }
      }
    }
  }
}
