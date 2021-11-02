package com.simple.app.ocbc_project_task.features.dashboard.ui

import android.text.format.DateFormat
import androidx.lifecycle.*
import com.google.gson.Gson
import com.simple.app.ocbc_project_task.common.domain.exception.ApiError
import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUsecaseResponse
import com.simple.app.ocbc_project_task.features.dashboard.data.model.AccountBalancesData
import com.simple.app.ocbc_project_task.features.dashboard.data.model.TransactionsStatementData
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardAccountBalancesUseCase
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardTransactionStatementUseCase
import java.math.BigDecimal

class DashboardViewModel(
    private val dashboardTransactionStatementUseCase: DashboardTransactionStatementUseCase,
    private val dashboardAccountBalancesUseCase: DashboardAccountBalancesUseCase
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

    private fun AccountBalancesData.toAvailableBalanceUi(): String =
        "${this.currency} ${this.availableBalance}"

    fun getAccountBalancesData() {
        dashboardAccountBalancesUseCase.invoke(
            viewModelScope, null,
            object : BaseUsecaseResponse<AccountBalancesData> {
                override fun onSuccess(result: AccountBalancesData) {
                    _accountBalancesData.value = result
                }

                override fun onError(apiError: ApiError?) {
//                    _dashboardError.value = apiError?.getErrorMessage()
                    _accountBalancesData.value = createAccountBalanceMockData()
                }

            }
        )
    }

    fun getMiniStatementData() {
        dashboardTransactionStatementUseCase.invoke(
            viewModelScope, null,
            object : BaseUsecaseResponse<TransactionsStatementData> {
                override fun onSuccess(transactionsStatementData: TransactionsStatementData) {
                    _miniStatementData.value = transactionsStatementData.data
                }

                override fun onError(apiError: ApiError?) {
                    _miniStatementData.value = createTransactionStatementMockData().data
//                    _dashboardError.value = apiError?.getErrorMessage()
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
                        itemTransaction.type ?: "",
                        itemTransaction.toTransactionDate(),
                        itemTransaction.description ?: ""
                    )
                )
            }
        }
        return transferStatementUiData.toList()
    }

    private fun TransactionsStatementData.Data.toMonthYearConvert(): String =
        (DateFormat.format("MM", this.date) as String) + " " +
                (DateFormat.format("yyyy", this.date) as String)

    private fun TransactionsStatementData.Data.toTransactionDate(): String =
        (DateFormat.format("dd", this.date) as String) + " " +
                (DateFormat.format("MM", this.date) as String) + " " +
                (DateFormat.format("yyyy", this.date) as String)

    fun createTransactionStatementMockData(): TransactionsStatementData {
        val gson = Gson()
        val jsonString =
            "{\"status\":\"success\",\"data\":[{\"id\":\"39222a3e-3890-4091-8807-d92707355f8c\",\"type\":\"receive\",\"amount\":18.5,\"currency\":\"SGD\",\"from\":{\"accountNo\":\"1234-000-1234\",\"accountHolderName\":\"Max Yee\"},\"description\":null,\"date\":\"2021-09-12T02:13:03.054Z\"},{\"id\":\"3c6bee24-072d-4919-aff9-b87d1f4c3895\",\"type\":\"receive\",\"amount\":23,\"currency\":\"SGD\",\"from\":{\"accountNo\":\"8182-321-9921\",\"accountHolderName\":\"Daniel\"},\"description\":\"lunch\",\"date\":\"2021-09-12T01:00:03.054Z\"},{\"id\":\"3992c7d3-ad34-401a-b3c8-ce6df2471e37\",\"type\":\"transfer\",\"amount\":1800,\"currency\":\"SGD\",\"to\":{\"accountNo\":\"8013-777-3232\",\"accountHolderName\":\"Jason\"},\"description\":\"September Rental\",\"date\":\"2021-09-11T02:13:03.054Z\"},{\"id\":\"001a18a2-fb41-46a2-bd20-0c389546ab64\",\"type\":\"receive\",\"amount\":450,\"currency\":\"SGD\",\"from\":{\"accountNo\":\"1234-000-1234\",\"accountHolderName\":\"Max Yee\"},\"description\":\"rental\",\"date\":\"2021-09-11T01:00:03.054Z\"},{\"id\":\"0541c91f-dce4-4bf9-ba98-5c8c2f7afe62\",\"type\":\"transfer\",\"amount\":23.2,\"currency\":\"SGD\",\"to\":{\"accountNo\":\"4489-991-0023\",\"accountHolderName\":\"Jane\"},\"description\":\"cafe\",\"date\":\"2021-09-11T01:00:03.054Z\"}]}"
        return gson.fromJson(jsonString, TransactionsStatementData::class.java)
    }

    fun createAccountBalanceMockData(): AccountBalancesData {
        val gson = Gson()
        val jsonString = "{\"status\":\"failed\",\"availableBalance\":10000,\"currency\":\"SGD\"}"
        return gson.fromJson(jsonString, AccountBalancesData::class.java)
    }
}