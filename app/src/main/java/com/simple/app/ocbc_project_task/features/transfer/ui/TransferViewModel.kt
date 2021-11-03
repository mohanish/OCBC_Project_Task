package com.simple.app.ocbc_project_task.features.transfer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
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
                override fun onSuccess(transferAmountData: TransferAmountData) {
                    _transferResult.value = transferAmountData.data
                }

                override fun onError(apiError: ApiError?) {
                    _transferError.value = apiError?.getErrorMessage()
                }
            }
        )
    }

    fun getPayees() {
        transferPayeesUseCase.invoke(
            viewModelScope, null,
            object : BaseUsecaseResponse<PayeesData> {
                override fun onSuccess(payeesData: PayeesData) {
                    _payeesResult.value = payeesData.data
                }

                override fun onError(apiError: ApiError?) {
                    _payeesResultError.value = apiError?.getErrorMessage()
                }
            }
        )
    }

    fun createPayeesData(): PayeesData? {
        val gson = Gson()
        val jsonString =
            "{\"status\":\"success\",\"data\":[{\"id\":\"8a6da1a4-6f5f-4b53-9b90-0f8c57661a5d\",\"accountNo\":\"8013-777-3232\",\"accountHolderName\":\"Jason\"},{\"id\":\"19bbc716-ddc3-48d1-a6f9-bb7b96749826\",\"accountNo\":\"4489-991-0023\",\"accountHolderName\":\"Jane\"},{\"id\":\"bcb6f34e-412d-4424-8aa9-59a299f57eb1\",\"accountNo\":\"1234-000-1234\",\"accountHolderName\":\"Max Yee\"},{\"id\":\"0348923c-cb08-4413-8683-5430769a5ee9\",\"accountNo\":\"8182-321-9921\",\"accountHolderName\":\"Daniel\"}]}"
        return gson.fromJson(jsonString, PayeesData::class.java)
    }

    fun createTransferData(): TransferAmountData? {
        val gson = Gson()
        val jsonString =
            "{\"status\":\"success\",\"data\":{\"id\":\"4a75bc0a-dccd-4d83-b5d8-4a9178894a19\",\"recipientAccountNo\":\"4992-992-9021\",\"amount\":100,\"date\":\"2021-09-12T00:00:00.000Z\",\"description\":\"room rental\"}}"
        return gson.fromJson(jsonString, TransferAmountData::class.java)
    }
}