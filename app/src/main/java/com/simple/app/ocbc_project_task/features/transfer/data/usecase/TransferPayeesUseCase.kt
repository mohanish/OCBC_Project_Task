package com.simple.app.ocbc_project_task.features.transfer.data.usecase

import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUseCase
import com.simple.app.ocbc_project_task.features.transfer.data.TransferRepository
import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData

open class TransferPayeesUseCase(
    private val transferRepository: TransferRepository
) : BaseUseCase<PayeesData, Any?>() {

    override suspend fun run(params: Any?): PayeesData {
        return transferRepository.getPayees(params)
    }
}