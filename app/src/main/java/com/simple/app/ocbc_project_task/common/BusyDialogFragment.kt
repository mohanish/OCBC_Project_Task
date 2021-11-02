package com.simple.app.ocbc_project_task.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.simple.app.ocbc_project_task.R

class BusyDialogFragment : DialogFragment() {
    companion object {
        private const val FRAGMENT_TAG = "busy"
        private fun newInstance() = BusyDialogFragment()
        fun show(supportFragmentManager: FragmentManager): BusyDialogFragment {
            val dialog = newInstance()
            // prevent dismiss by user click
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, FRAGMENT_TAG)
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // make white background transparent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return requireActivity().layoutInflater.inflate(R.layout.busy_progress_bar, container)
    }

    override fun onStart() {
        super.onStart()
        // remove black outer overlay, or change opacity
        dialog?.window?.also { window ->
            window.attributes?.also { attributes ->
                attributes.dimAmount = 0.1f
                window.attributes = attributes
            }
        }
    }
}