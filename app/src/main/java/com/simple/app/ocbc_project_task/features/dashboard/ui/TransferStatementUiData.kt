package com.simple.app.ocbc_project_task.features.dashboard.ui

import java.math.BigDecimal

sealed class TransferStatementUiData {

    data class MonthHeader(val month: String) : TransferStatementUiData()
    data class MiniStatementItem(
        val amount: BigDecimal,
        val currency: String,
        val description: String,
        val date: String,
        val type: String
    ) :
        TransferStatementUiData()
}