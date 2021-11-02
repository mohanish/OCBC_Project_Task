package com.simple.app.ocbc_project_task.features.transfer.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@TransferFragment).navigateUp();
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, onBackPressedCallback
        )
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
                    makeTransfer(
                        layout.makeTransferRecipient.text.toString(),
                        layout.makeTransferAmount.text.toString().toBigDecimal(),
                        layout.makeTransferDate.text.toString(),
                        layout.makeTransferDescription.text.toString()
                    )
                }
                transferResult.observe {
                    val action =
                        TransferFragmentDirections.actionTransferFragmentToDashboardFragment()
                    view.findNavController().navigate(action)
                }
            }
        }
    }

    private fun onRecipientSelectedClick(recipient: PayeesData.Data) {
        layout.makeTransferRecipient.setText(recipient.accountHolderName)
        recipientDialog?.dismiss()
    }
}