package dev.skrilltrax.blockka.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

import dev.skrilltrax.blockka.R

class AddContactFragment : BottomSheetDialogFragment() {

  private var behavior: BottomSheetBehavior<FrameLayout>? = null
  private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
    override fun onSlide(bottomSheet: View, slideOffset: Float) {
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
      if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
        dismiss()
      }
    }
  }

  private lateinit var addManually: MaterialButton
  private lateinit var addFromContacts: MaterialButton

  override fun getTheme(): Int {
    return R.style.BottomSheetDialogTheme
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_add_contact, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

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
        addManually = dialog.findViewById(R.id.add_manually)!!
        addManually.setOnClickListener {
          dismiss()
          onAddManual()
        }

        addFromContacts = dialog.findViewById(R.id.add_from_contacts)!!
        addFromContacts.setOnClickListener {
          dismiss()
          onAddContacts()
        }

      }
    })
  }

  private fun onAddManual() {
    setFragmentResult(REQUEST_ADD_CONTACTS, bundleOf(REQUEST_ADD to REQUEST_ADD_MANUAL))
  }

  private fun onAddContacts() {
    setFragmentResult(REQUEST_ADD_CONTACTS, bundleOf(REQUEST_ADD to REQUEST_ADD_CONTACTS))
  }

  override fun dismiss() {
    super.dismiss()
    behavior?.removeBottomSheetCallback(bottomSheetCallback)
  }

  companion object {
    const val REQUEST_ADD_TYPE = "request_add_type"
    const val REQUEST_ADD = "request_add"
    const val REQUEST_ADD_MANUAL = "request_add_manual"
    const val REQUEST_ADD_CONTACTS = "request_add_contacts"
  }
}
