package com.simple.app.ocbc_project_task.features.dashboard.data

import com.simple.app.ocbc_project_task.features.dashboard.data.model.AccountBalancesData
import com.simple.app.ocbc_project_task.features.dashboard.data.model.TransactionsStatementData

interface DashboardRepository {
    suspend fun accountTransactions(params: Any?): TransactionsStatementData
    suspend fun accountBalances(params: Any?): AccountBalancesData
}