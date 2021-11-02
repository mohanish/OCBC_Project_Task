package com.simple.app.ocbc_project_task.features.dashboard

import com.simple.app.ocbc_project_task.features.BaseViewModelTests
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardAccountBalancesUseCase
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardTransactionStatementUseCase
import com.simple.app.ocbc_project_task.features.dashboard.ui.DashboardViewModel
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest : BaseViewModelTests<DashboardViewModel>() {

    @Mock
    lateinit var dashboardTransactionStatementUseCase: DashboardTransactionStatementUseCase

    @Mock
    lateinit var dashboardAccountBalancesUseCase: DashboardAccountBalancesUseCase

    override fun createViewModel(): DashboardViewModel {
        return DashboardViewModel(
            dashboardTransactionStatementUseCase = dashboardTransactionStatementUseCase,
            dashboardAccountBalancesUseCase = dashboardAccountBalancesUseCase
        )
    }
}