package com.simple.app.ocbc_project_task.features.transfer.ui.recipients

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.simple.app.ocbc_project_task.R
import com.simple.app.ocbc_project_task.common.ui.BottomSheetDialogFragment
import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData
import kotlinx.android.synthetic.main.layout_recipient.*

class RecipientDialog(
    private val payeesData: List<PayeesData.Data>,
    private val onApplyClickListener: (selectedRecipient: PayeesData.Data) -> Unit,
) : BottomSheetDialogFragment() {

    private val recipientListAdapter = RecipientListAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)
            .apply {
                setOnShowListener {
                    val bottomSheet =
                        findViewById(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
                    BottomSheetBehavior.from(bottomSheet!!).run {
                        peekHeight = Resources.getSystem().displayMetrics.heightPixels
                    }
                }
            }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.layout_recipient, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipientListAdapter.setItems(payeesData)
        rvChequeActivityFilters.adapter = recipientListAdapter
        recipientListAdapter.setOnClickListener(onApplyClickListener)
    }
}