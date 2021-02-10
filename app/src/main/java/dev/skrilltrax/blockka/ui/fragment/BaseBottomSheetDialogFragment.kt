package dev.skrilltrax.blockka.ui.fragment

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

  protected val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
    val view by lazy { requireActivity().window.decorView }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
      val offset = 1 - (0.1f * slideOffset)
      view.animate().scaleY(offset).scaleX(offset).start()
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
      if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
        dismiss()
      }
    }
  }

}
