package com.simple.app.ocbc_project_task.features.dashboard.data

import com.simple.app.ocbc_project_task.features.dashboard.data.model.AccountBalancesData
import com.simple.app.ocbc_project_task.features.dashboard.data.model.TransactionsStatementData
import retrofit2.http.POST

interface DashboardApi {

    @POST("/account/transactions")
    suspend fun accountTransactions(): TransactionsStatementData

    @POST("/account/balances")
    suspend fun accountBalances(): AccountBalancesData
}