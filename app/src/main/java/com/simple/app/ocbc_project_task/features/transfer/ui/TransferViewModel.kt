package com.simple.app.ocbc_project_task.features.transfer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.app.ocbc_project_task.common.domain.exception.ApiError
import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUsecaseResponse
import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData
import com.simple.app.ocbc_project_task.features.transfer.data.model.TransferAmountData
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferPayeesUseCase
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferUseCase
import java.math.BigDecimal

class TransferViewModel(
    private val transferUseCase: TransferUseCase,
    private val transferPayeesUseCase: TransferPayeesUseCase
) : ViewModel() {

    private val _transferResult = MutableLiveData<TransferAmountData.Data>()
    val transferResult: LiveData<TransferAmountData.Data> = _transferResult

    private val _transferError = MutableLiveData<String>()
    val transferError: LiveData<String> = _transferError

    private val _payeesResult = MutableLiveData<List<PayeesData.Data>>()
    val payeesResult: LiveData<List<PayeesData.Data>> = _payeesResult

    private val _payeesResultError = MutableLiveData<String>()
    val payeesResultError: LiveData<String> = _payeesResultError

    fun initialise() {
        getPayees()
    }

    fun makeTransfer(
        recipientAccountNo: String,
        amount: BigDecimal,
        date: String,
        description: String
    ) {
        val transferRequestParams = TransferUseCase.TransferRequestParams(
            recipientAccountNo, amount, date, description
        )
        transferUseCase.invoke(
            viewModelScope, transferRequestParams,
            object : BaseUsecaseResponse<TransferAmountData> {
                override fun onSuccess(result: TransferAmountData) {
                    _transferResult.value = result.data
                }

                override fun onError(apiError: ApiError?) {
                    _transferError.value = apiError?.getErrorMessage()
                }
            }
        )
    }

    private fun getPayees() {
        transferPayeesUseCase.invoke(
            viewModelScope, null,
            object : BaseUsecaseResponse<PayeesData> {
                override fun onSuccess(result: PayeesData) {
                    _payeesResult.value = result.data
                }

                override fun onError(apiError: ApiError?) {
                    _payeesResultError.value = apiError?.getErrorMessage()
                }
            }
        )
    }
}