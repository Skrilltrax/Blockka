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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

import dev.skrilltrax.blockka.R
import dev.skrilltrax.blockka.ui.viewmodel.ContactViewModel

@AndroidEntryPoint
class EnterNumberFragment : BottomSheetDialogFragment() {

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

  private val viewmodel: ContactViewModel by activityViewModels()
  private lateinit var numberEditTextView: TextInputEditText
  private lateinit var numberTextInputLayout: TextInputLayout
  private lateinit var doneButton: MaterialButton

  override fun getTheme(): Int {
    return R.style.BottomSheetDialogTheme
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_enter_number, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    observeViewTree(view)
    findViews(view)
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

  private fun findViews(view: View) {
    numberEditTextView = view.findViewById(R.id.tiet_number)
    numberTextInputLayout = view.findViewById(R.id.til_number)
    doneButton = view.findViewById(R.id.done)
  }

  private fun setClickListeners() {
    doneButton.setOnClickListener {
      val text = numberEditTextView.text
      if (text.isNullOrEmpty() || text.length < 10) {
        numberTextInputLayout.error = "Number should have at least 10 digits"
        return@setOnClickListener
      }

      viewmodel.addContact(text.toString())
      dismiss()
    }

    numberEditTextView.doOnTextChanged { _, _, _, _ -> numberTextInputLayout.error = null }
  }

  companion object {
    const val REQUEST_ADD_TYPE = "request_add_type"
  }
}
