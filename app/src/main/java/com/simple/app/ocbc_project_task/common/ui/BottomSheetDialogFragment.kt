package com.simple.app.ocbc_project_task.common.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    /**
     * Bottom Sheet Height in percentage
     * Default to be 100% height
     */
    private val bottomSheetHeightPercentage: Int = MAX_DIALOG_HEIGHT

    private val bottomSheetBehavior: BottomSheetBehavior<View>?
        get() {
            val parent = dialog?.let { getContainerLayout(it) }
            return parent?.let { BottomSheetBehavior.from(it) }
        }
    private val bottomSheetCallback = NextBottomSheetCallback()
    private val percentageRange = IntRange(PERCENTAGE_0, PERCENTAGE_100)
    private val onViewCreatedListeners = mutableListOf<(View, Bundle?) -> Unit>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            bottomSheetBehavior?.setBottomSheetCallback(bottomSheetCallback)
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewHeight(view)

        onViewCreatedListeners.forEach { it.invoke(view, savedInstanceState) }
    }

    fun setupViewHeight(view: View) {
        val observer = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val parent = getContainerLayout(dialog)
                val layoutParams = parent?.layoutParams ?: return

                // By default, the parent's height is setDeviceId to WRAP_CONTENT, which is why
                // child layouts do not expand to full height
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                layoutParams.height = getHeight(parent)
                parent.layoutParams = layoutParams
                // We have to remove this listener after the first call, otherwise this
                // will be invoked repeatedly causing the app to stutter
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(observer)
    }

    /**
     * Return new height of bottom sheet, depends on conditions.
     * New height is calculated from percentage of fragment height.
     *
     * @param containerLayout the bottom sheet container
     * @return LayoutParams.MATCH_PARENT, if forceFullHeight is TRUE
     *         LayoutParams.WRAP_CONTENT, if content height less than new height
     *         newHeight, if content height more than new height
     */
    private fun getHeight(containerLayout: FrameLayout): Int {
        val newHeight = (containerLayout.parent as View).height
            .times(bottomSheetHeightPercentage)
            .div(PERCENTAGE_100)

        return when {
            !isHeightPercentageValid(bottomSheetHeightPercentage) ||
                    containerLayout.height < newHeight -> ViewGroup.LayoutParams.WRAP_CONTENT
            else -> newHeight
        }
    }

    private fun isHeightPercentageValid(heightPercentage: Int): Boolean =
        percentageRange.contains(heightPercentage)

    /**
     * Returns the dialog's containing parent.
     */
    private fun getContainerLayout(dialog: Dialog?): FrameLayout? {
        val id = com.google.android.material.R.id.design_bottom_sheet
        return dialog?.findViewById(id)
    }


    private inner class NextBottomSheetCallback : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(v: View, state: Int) {
            if (state == BottomSheetBehavior.STATE_HIDDEN) {
                onStateHidden()
                dismissAllowingStateLoss()
            }
        }

        override fun onSlide(v: View, offset: Float) {
            // do nothing
        }
    }

    protected open fun onStateHidden(): Boolean = true

    companion object {
        const val PERCENTAGE_0 = 0
        const val PERCENTAGE_100 = 100
        const val MAX_DIALOG_HEIGHT = 100
    }
}