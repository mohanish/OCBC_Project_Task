package com.simple.app.ocbc_project_task.features.transfer.data.usecase

import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUseCase
import com.simple.app.ocbc_project_task.features.transfer.data.TransferRepository
import com.simple.app.ocbc_project_task.features.transfer.data.model.TransferAmountData
import java.math.BigDecimal

open class TransferUseCase constructor(
    private val transferRepository: TransferRepository
) : BaseUseCase<TransferAmountData, Any?>() {

    override suspend fun run(params: Any?): TransferAmountData {
        return transferRepository.makeTransfer(params)
    }

    data class TransferRequestParams(
        val recipientAccountNo: String,
        val amount: BigDecimal,
        val date: String,
        val description: String
    )
}