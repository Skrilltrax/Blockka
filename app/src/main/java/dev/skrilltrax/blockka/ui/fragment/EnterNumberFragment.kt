package dev.skrilltrax.blockka.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import dev.skrilltrax.blockka.R
import dev.skrilltrax.blockka.databinding.FragmentEnterNumberBinding
import dev.skrilltrax.blockka.ui.viewmodel.ContactViewModel

@AndroidEntryPoint
class EnterNumberFragment : BaseBottomSheetDialogFragment() {

  private var behavior: BottomSheetBehavior<FrameLayout>? = null
  private val viewModel: ContactViewModel by activityViewModels()
  private lateinit var binding: FragmentEnterNumberBinding

  override fun getTheme(): Int {
    return R.style.BottomSheetDialogTheme
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentEnterNumberBinding.inflate(layoutInflater, container, true)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    observeViewTree(view)
    setClickListeners()
  }

  override fun dismiss() {
    super.dismiss()
    behavior?.removeBottomSheetCallback(bottomSheetCallback)
  }

  private fun observeViewTree(view: View) {
    view.viewTreeObserver.addOnGlobalLayoutListener(object :
      ViewTreeObserver.OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        val dialog = dialog as BottomSheetDialog? ?: return
        behavior = dialog.behavior
        behavior?.apply {
          state = BottomSheetBehavior.STATE_EXPANDED
          peekHeight = 0
          addBottomSheetCallback(bottomSheetCallback)
        }
      }
    })
  }

  private fun setClickListeners() {
    with(binding) {
      done.setOnClickListener {
        val number = tietNumber.text
        if (number.isNullOrEmpty() || number.length < 10) {
          tilNumber.error = "Number should have at least 10 digits"
          return@setOnClickListener
        }

        val name = tietName.text
        if (name.isNullOrEmpty()) {
          viewModel.addContact(number.toString())
        } else {
          viewModel.addContact(number.toString(), name.toString())
        }
        dismiss()
      }

      tietNumber.doOnTextChanged { _, _, _, _ -> tilNumber.error = null }
    }
  }

  companion object {
    const val REQUEST_ADD_TYPE = "request_add_type"
  }
}
