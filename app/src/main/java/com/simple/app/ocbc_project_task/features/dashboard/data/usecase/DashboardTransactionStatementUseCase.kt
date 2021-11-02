package com.simple.app.ocbc_project_task.features.dashboard.data.usecase

import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUseCase
import com.simple.app.ocbc_project_task.features.dashboard.data.DashboardRepository
import com.simple.app.ocbc_project_task.features.dashboard.data.model.TransactionsStatementData

class DashboardTransactionStatementUseCase constructor(
    private val dashboardRepository: DashboardRepository
) : BaseUseCase<TransactionsStatementData, Any?>() {

    override suspend fun run(params: Any?): TransactionsStatementData {
        return dashboardRepository.accountTransactions(params)
    }
}