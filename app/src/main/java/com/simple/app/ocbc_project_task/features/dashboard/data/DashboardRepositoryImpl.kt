package com.simple.app.ocbc_project_task.features.dashboard.data

import com.simple.app.ocbc_project_task.features.dashboard.data.model.AccountBalancesData
import com.simple.app.ocbc_project_task.features.dashboard.data.model.TransactionsStatementData

class DashboardRepositoryImpl(private val dashboardApi: DashboardApi) : DashboardRepository {
    override suspend fun accountTransactions(params: Any?): TransactionsStatementData {
        return dashboardApi.accountTransactions()
    }

    override suspend fun accountBalances(params: Any?): AccountBalancesData {
        return dashboardApi.accountBalances()
    }
}