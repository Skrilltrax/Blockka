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
import com.google.android.material.button.MaterialButton
import dev.skrilltrax.blockka.R

class AddContactFragment : BaseBottomSheetDialogFragment() {

  private var behavior: BottomSheetBehavior<FrameLayout>? = null
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
    return inflater.inflate(R.layout.fragment_add_contact, container, true)
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
    setFragmentResult(RESULT_ADD_CONTACTS, bundleOf(RESULT_ADD to RESULT_ADD_MANUAL))
  }

  private fun onAddContacts() {
    setFragmentResult(RESULT_ADD_CONTACTS, bundleOf(RESULT_ADD to RESULT_ADD_CONTACTS))
  }

  override fun dismiss() {
    super.dismiss()
    behavior?.removeBottomSheetCallback(bottomSheetCallback)
  }

  companion object {
    const val REQUEST_ADD_TYPE = "request_add_type"
    const val RESULT_ADD = "result_add"
    const val RESULT_ADD_MANUAL = "result_add_manual"
    const val RESULT_ADD_CONTACTS = "result_add_contacts"
  }
}
