package com.simple.app.ocbc_project_task.common.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.simple.app.ocbc_project_task.common.BusyDialogFragment

abstract class ViewBindingFragment<Binding : ViewBinding>(
    @LayoutRes private val contentLayoutId: Int? = null
) : Fragment() {

    private var _layout: Binding? = null

    protected val layout: Binding
        get() = _layout!!

    private lateinit var busyDialog: BusyDialogFragment

    fun showBusyProgressDialog(shouldShow: Boolean) {
        if (shouldShow) {
            busyDialog = BusyDialogFragment.show(requireActivity().supportFragmentManager)
        } else {
            busyDialog.dismiss()
        }
    }

    /**
     * Returns the [ViewBinding] class for this Fragment's layout.
     */
    abstract fun initializeLayoutBinding(view: View): Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = contentLayoutId?.let { inflater.inflate(it, container, false) }
        if (view != null) {
            _layout = initializeLayoutBinding(view)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layout = null
    }


}