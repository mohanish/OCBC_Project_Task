package com.simple.app.ocbc_project_task.features.dashboard

import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenManager
import com.simple.app.ocbc_project_task.features.BaseViewModelTests
import com.simple.app.ocbc_project_task.features.dashboard.data.model.AccountBalancesData
import com.simple.app.ocbc_project_task.features.dashboard.data.model.TransactionsStatementData
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardAccountBalancesUseCase
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardTransactionStatementUseCase
import com.simple.app.ocbc_project_task.features.dashboard.ui.DashboardViewModel
import com.simple.app.ocbc_project_task.features.dashboard.ui.TransferStatementUiData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class DashboardViewModelTest : BaseViewModelTests<DashboardViewModel>() {

    @Mock
    lateinit var dashboardTransactionStatementUseCase: DashboardTransactionStatementUseCase

    @Mock
    lateinit var dashboardAccountBalancesUseCase: DashboardAccountBalancesUseCase

    @Mock
    lateinit var tokenService: TokenManager

    @Mock
    private lateinit var accountBalanceObserver: Observer<String>

    @Mock
    private lateinit var miniStatementObserver: Observer<List<TransferStatementUiData?>>

    override fun createViewModel(): DashboardViewModel {
        return DashboardViewModel(
            dashboardTransactionStatementUseCase = dashboardTransactionStatementUseCase,
            dashboardAccountBalancesUseCase = dashboardAccountBalancesUseCase,
            tokenService = tokenService
        )
    }

    @Test
    fun `should get account balance and mini statement data`() {
        val createAccountBalanceMockData = createAccountBalanceMockData()
        testCoroutineRule.runBlockingTest {
            whenever(dashboardAccountBalancesUseCase.run()).thenReturn(createAccountBalanceMockData)
            whenever(dashboardTransactionStatementUseCase.run()).thenReturn(
                createTransactionStatementMockData()
            )
        }
        vm.initialise()
    }

    private fun createTransactionStatementMockData(): TransactionsStatementData {
        val gson = Gson()
        val jsonString =
            "{\"status\":\"success\",\"data\":[{\"id\":\"39222a3e-3890-4091-8807-d92707355f8c\",\"type\":\"receive\",\"amount\":18.5,\"currency\":\"SGD\",\"from\":{\"accountNo\":\"1234-000-1234\",\"accountHolderName\":\"Max Yee\"},\"description\":null,\"date\":\"2021-09-12T02:13:03.054Z\"},{\"id\":\"3c6bee24-072d-4919-aff9-b87d1f4c3895\",\"type\":\"receive\",\"amount\":23,\"currency\":\"SGD\",\"from\":{\"accountNo\":\"8182-321-9921\",\"accountHolderName\":\"Daniel\"},\"description\":\"lunch\",\"date\":\"2021-09-12T01:00:03.054Z\"},{\"id\":\"3992c7d3-ad34-401a-b3c8-ce6df2471e37\",\"type\":\"transfer\",\"amount\":1800,\"currency\":\"SGD\",\"to\":{\"accountNo\":\"8013-777-3232\",\"accountHolderName\":\"Jason\"},\"description\":\"September Rental\",\"date\":\"2021-09-11T02:13:03.054Z\"},{\"id\":\"001a18a2-fb41-46a2-bd20-0c389546ab64\",\"type\":\"receive\",\"amount\":450,\"currency\":\"SGD\",\"from\":{\"accountNo\":\"1234-000-1234\",\"accountHolderName\":\"Max Yee\"},\"description\":\"rental\",\"date\":\"2021-09-11T01:00:03.054Z\"},{\"id\":\"0541c91f-dce4-4bf9-ba98-5c8c2f7afe62\",\"type\":\"transfer\",\"amount\":23.2,\"currency\":\"SGD\",\"to\":{\"accountNo\":\"4489-991-0023\",\"accountHolderName\":\"Jane\"},\"description\":\"cafe\",\"date\":\"2021-09-11T01:00:03.054Z\"}]}"
        return gson.fromJson(jsonString, TransactionsStatementData::class.java)
    }

    private fun createAccountBalanceMockData(): AccountBalancesData {
        val gson = Gson()
        val jsonString = "{\"status\":\"failed\",\"availableBalance\":10000,\"currency\":\"SGD\"}"
        return gson.fromJson(jsonString, AccountBalancesData::class.java)
    }
}