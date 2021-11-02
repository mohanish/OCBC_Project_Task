package com.simple.app.ocbc_project_task.features.transfer.data

import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData
import com.simple.app.ocbc_project_task.features.transfer.data.model.TransferAmountData

interface TransferRepository {
    suspend fun makeTransfer(params: Any?): TransferAmountData
    suspend fun getPayees(params: Any?): PayeesData
}