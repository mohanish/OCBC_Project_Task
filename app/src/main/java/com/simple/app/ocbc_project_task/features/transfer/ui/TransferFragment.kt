package com.simple.app.ocbc_project_task.features.transfer.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.simple.app.ocbc_project_task.R
import com.simple.app.ocbc_project_task.common.binding.ViewBindingFragment
import com.simple.app.ocbc_project_task.common.binding.withViewLifecycleOwner
import com.simple.app.ocbc_project_task.databinding.FragmentTransferBinding
import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData
import com.simple.app.ocbc_project_task.features.transfer.ui.recipients.RecipientDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransferFragment :
    ViewBindingFragment<FragmentTransferBinding>(R.layout.fragment_transfer) {

    private val transferViewModel: TransferViewModel by viewModel()
    private var recipientDialog: RecipientDialog? = null

    override fun initializeLayoutBinding(view: View): FragmentTransferBinding {
        return FragmentTransferBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(transferViewModel) {
            withViewLifecycleOwner {
                getPayees()
                payeesResult.observe { payees ->
                    layout.makeTransferRecipient.setText(payees[0].accountHolderName)
                    recipientDialog = RecipientDialog(
                        payeesResult.value!!,
                        onApplyClickListener = this@TransferFragment::onRecipientSelectedClick
                    )
                }
                layout.makeTransferRecipient.setOnClickListener {
                    if (recipientDialog != null)
                        recipientDialog!!.show(childFragmentManager, "")
                }
                layout.btnCancelTransfer.setOnClickListener {

                }
                layout.btnSubmitTransfer.setOnClickListener {
                    val recipient = layout.makeTransferRecipient.text.toString()
                    val amount = layout.makeTransferAmount.text.toString()
                    val date = layout.makeTransferDate.text.toString()
                    val description = layout.makeTransferDescription.text.toString()
                    if (recipient.isEmpty() && amount.isEmpty() && date.isEmpty() && description.isEmpty())
                        return@setOnClickListener
                    makeTransfer(recipient, amount.toBigDecimal(), date, description)
                }
                transferResult.observe {
                    Toast.makeText(context, R.string.success_transfer, Toast.LENGTH_LONG).show()
                    requireActivity().onBackPressed()
                }
                transferError.observe {
                    Toast.makeText(context, R.string.error_transfer, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onRecipientSelectedClick(recipient: PayeesData.Data) {
        layout.makeTransferRecipient.setText(recipient.accountHolderName)
        recipientDialog?.dismiss()
    }
}