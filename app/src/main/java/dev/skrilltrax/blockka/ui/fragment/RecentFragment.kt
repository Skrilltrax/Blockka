package dev.skrilltrax.blockka.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.skrilltrax.blockka.databinding.FragmentHomeBinding
import dev.skrilltrax.blockka.ui.MainActivity
import dev.skrilltrax.blockka.ui.adapter.RecentContactListAdapter
import dev.skrilltrax.blockka.ui.viewmodel.ContactViewModel
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class RecentFragment : Fragment() {

  private val viewmodel: ContactViewModel by activityViewModels()
  private lateinit var binding: FragmentHomeBinding
  private val activity by lazy(LazyThreadSafetyMode.NONE) { (requireActivity() as MainActivity) }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentHomeBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.recyclerview.apply {
      adapter = RecentContactListAdapter(emptyList()).onItemClicked { holder, item ->
      }
    }

    lifecycleScope.launchWhenResumed {
      viewmodel.getAllRecentContacts().collect {
        (binding.recyclerview.adapter as RecentContactListAdapter).updateList(it)
      }
    }
  }

}
