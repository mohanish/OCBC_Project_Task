package com.simple.app.ocbc_project_task.features.transfer.data

import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData
import com.simple.app.ocbc_project_task.features.transfer.data.model.TransferAmountData
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferUseCase

class TransferRepositoryImpl(private val transferApi: TransferApi) : TransferRepository {

    override suspend fun makeTransfer(params: Any?): TransferAmountData {
        val transferRequestParams = params as TransferUseCase.TransferRequestParams
        return transferApi.makeTransfer(transferRequestParams)
    }

    override suspend fun getPayees(params: Any?): PayeesData {
        return transferApi.getPayees()
    }
}