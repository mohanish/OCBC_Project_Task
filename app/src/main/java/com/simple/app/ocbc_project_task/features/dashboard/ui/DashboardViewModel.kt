package com.simple.app.ocbc_project_task.features.dashboard.ui

import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.*
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenManager
import com.simple.app.ocbc_project_task.common.domain.exception.ApiError
import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUsecaseResponse
import com.simple.app.ocbc_project_task.features.dashboard.data.model.AccountBalancesData
import com.simple.app.ocbc_project_task.features.dashboard.data.model.TransactionsStatementData
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardAccountBalancesUseCase
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardTransactionStatementUseCase
import java.math.BigDecimal

class DashboardViewModel(
    private val dashboardTransactionStatementUseCase: DashboardTransactionStatementUseCase,
    private val dashboardAccountBalancesUseCase: DashboardAccountBalancesUseCase,
    private val tokenService: TokenManager
) : ViewModel() {

    private val _miniStatementData = MutableLiveData<List<TransactionsStatementData.Data>>()
    val miniStatementUiData = MediatorLiveData<List<TransferStatementUiData?>>().apply {
        addSource(_miniStatementData) { this.value = filterMiniStatementData(it) }
    }

    private val _accountBalancesData = MutableLiveData<AccountBalancesData>()
    val accountBalancesData: LiveData<String> = Transformations.map(_accountBalancesData) {
        it.toAvailableBalanceUi()
    }

    private val _dashboardError = MutableLiveData<String>()
    val dashboardError: LiveData<String> = _dashboardError

    fun initialise() {
        getAccountBalancesData()
        getMiniStatementData()
    }

    private fun AccountBalancesData.toAvailableBalanceUi(): String =
        "SGD ${this.availableBalance}"

    fun getAccountBalancesData() {
        dashboardAccountBalancesUseCase.invoke(
            viewModelScope, null,
            object : BaseUsecaseResponse<AccountBalancesData> {
                override fun onSuccess(result: AccountBalancesData) {
                    _accountBalancesData.value = result
                }

                override fun onError(apiError: ApiError?) {
                    _dashboardError.value = apiError?.getErrorMessage()
                }
            }
        )
    }

    fun getMiniStatementData() {
        dashboardTransactionStatementUseCase.invoke(
            viewModelScope, null,
            object : BaseUsecaseResponse<TransactionsStatementData> {
                override fun onSuccess(result: TransactionsStatementData) {
                    _miniStatementData.value = result.data
                }

                override fun onError(apiError: ApiError?) {
                    _dashboardError.value = apiError?.getErrorMessage()
                }
            }
        )
    }

    private fun filterMiniStatementData(list: List<TransactionsStatementData.Data>): List<TransferStatementUiData?> {
        val transferStatementUiData = mutableListOf<TransferStatementUiData?>()
        val sortedDateList = list.sortedByDescending { it.toMonthYearConvert() }
        val groupingDateList = sortedDateList.groupBy { it.toMonthYearConvert() }
        groupingDateList.forEach { itemHeader ->
            transferStatementUiData.add(
                TransferStatementUiData.MonthHeader(itemHeader.key)
            )
            itemHeader.value.forEach { itemTransaction ->
                transferStatementUiData.add(
                    TransferStatementUiData.MiniStatementItem(
                        itemTransaction.amount ?: BigDecimal.ZERO,
                        itemTransaction.currency ?: "",
                        itemTransaction.description ?: "",
                        itemTransaction.toTransactionDate(),
                        itemTransaction.type ?: ""
                    )
                )
            }
        }
        return transferStatementUiData.toList()
    }

    fun logout() {
        tokenService.clear()
    }

    private fun TransactionsStatementData.Data.toMonthYearConvert(): String =
        (DateFormat.format("MMMM", this.date) as String) + " " +
                (DateFormat.format("yyyy", this.date) as String)

    private fun TransactionsStatementData.Data.toTransactionDate(): String =
        (DateFormat.format("dd", this.date) as String) + "-" +
                (DateFormat.format("MM", this.date) as String) + "-" +
                (DateFormat.format("yyyy", this.date) as String)
}