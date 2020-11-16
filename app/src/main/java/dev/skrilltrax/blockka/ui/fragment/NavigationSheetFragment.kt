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
import dev.skrilltrax.blockka.ui.ListType

class NavigationSheetFragment(private val listType: ListType) : BottomSheetDialogFragment() {

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

  private lateinit var allContacts: MaterialButton
  private lateinit var recentContacts: MaterialButton

  override fun getTheme(): Int {
    return R.style.BottomSheetDialogTheme
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_navigation_sheet, container, false)
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
        allContacts = dialog.findViewById(R.id.all_contacts)!!
        allContacts.setOnClickListener {
          dismiss()
          onAllContacts()
        }
        recentContacts = dialog.findViewById(R.id.recent_contacts)!!
        recentContacts.setOnClickListener {
          dismiss()
          onRecentContacts()
        }

        when (listType) {
          ListType.ALL_CONTACTS -> allContacts.isPressed = true
          ListType.RECENT_CONTACTS -> recentContacts.isPressed = true
        }

      }
    })
  }

  private fun onAllContacts() {
    setFragmentResult(REQUEST_LIST_TYPE, bundleOf(RESULT_LIST to RESULT_ALL_CONTACTS))
  }

  private fun onRecentContacts() {
    setFragmentResult(REQUEST_LIST_TYPE, bundleOf(RESULT_LIST to RESULT_RECENT_CONTACTS))
  }

  override fun dismiss() {
    super.dismiss()
    behavior?.removeBottomSheetCallback(bottomSheetCallback)
  }

  companion object {
    const val REQUEST_LIST_TYPE = "request_list_type"
    const val RESULT_LIST = "result_list"
    const val RESULT_ALL_CONTACTS = "result_all_contacts"
    const val RESULT_RECENT_CONTACTS = "result_recent_contacts"
  }
}
