package dev.skrilltrax.blockka.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.selection.Selection
import dagger.hilt.android.AndroidEntryPoint
import dev.skrilltrax.blockka.R
import dev.skrilltrax.blockka.databinding.FragmentHomeBinding
import dev.skrilltrax.blockka.ui.MainActivity
import dev.skrilltrax.blockka.ui.adapter.ContactListAdapter
import dev.skrilltrax.blockka.ui.viewmodel.ContactViewModel
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

  private val viewModel: ContactViewModel by activityViewModels()
  private val activity by lazy(LazyThreadSafetyMode.NONE) { (requireActivity() as MainActivity) }
  private lateinit var binding: FragmentHomeBinding
  private var actionMode: ActionMode? = null

  private val actionModeCallback = object : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
      mode?.menuInflater?.inflate(R.menu.delete_menu, menu)
      return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
      return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
      when (item?.itemId) {
        R.id.delete -> {
          viewModel.deleteContacts((binding.recyclerview.adapter as ContactListAdapter).selectedContacts)
          mode?.finish()
        }
      }
      return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
      (binding.recyclerview.adapter as ContactListAdapter).requireSelectionTracker()
        .clearSelection()
      actionMode?.finish()
      actionMode = null
    }

  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentHomeBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.recyclerview.apply {
      adapter = ContactListAdapter(emptyList()).onItemClicked { holder, item ->
        Timber.d(item.toString())
      }.onSelectionChanged { selection ->
        doOnSelectionChanged(selection)
      }
      (adapter as ContactListAdapter).makeSelectable(this)
    }

    lifecycleScope.launchWhenResumed {
      viewModel.getAllContacts().collect {
        Timber.d(it.toString())
        (binding.recyclerview.adapter as ContactListAdapter).updateList(it)
      }
    }
  }

  private fun doOnSelectionChanged(selection: Selection<Long>) {
    if (actionMode == null) {
      actionMode = activity.startSupportActionMode(actionModeCallback) ?: return
    }
    if (!selection.isEmpty) {
      actionMode!!.title = "Delete contacts"
      actionMode!!.invalidate()
    } else {
      actionMode!!.finish()
    }
  }

  override fun onPause() {
    super.onPause()
    actionMode?.finish()
  }
}
