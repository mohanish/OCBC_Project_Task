package com.simple.app.ocbc_project_task.dependencies

import com.simple.app.ocbc_project_task.features.dashboard.data.DashboardApi
import com.simple.app.ocbc_project_task.features.dashboard.data.DashboardRepository
import com.simple.app.ocbc_project_task.features.dashboard.data.DashboardRepositoryImpl
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardAccountBalancesUseCase
import com.simple.app.ocbc_project_task.features.dashboard.data.usecase.DashboardTransactionStatementUseCase
import com.simple.app.ocbc_project_task.features.dashboard.ui.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DashboardModule = module {
    viewModel { DashboardViewModel(get(), get(), get()) }
    single { createDashboardRepository(get()) }
    single { createDashboardUseCase(get()) }
    single { createDashboardAccountBalanceUseCase(get()) }
}

fun createDashboardRepository(dashboardApi: DashboardApi): DashboardRepository {
    return DashboardRepositoryImpl(dashboardApi = dashboardApi)
}

fun createDashboardUseCase(dashboardRepository: DashboardRepository): DashboardTransactionStatementUseCase {
    return DashboardTransactionStatementUseCase(dashboardRepository)
}

fun createDashboardAccountBalanceUseCase(dashboardRepository: DashboardRepository): DashboardAccountBalancesUseCase {
    return DashboardAccountBalancesUseCase(dashboardRepository)
}