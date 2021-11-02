package com.simple.app.ocbc_project_task.features.dashboard.data.usecase

import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUseCase
import com.simple.app.ocbc_project_task.features.dashboard.data.DashboardRepository
import com.simple.app.ocbc_project_task.features.dashboard.data.model.AccountBalancesData

class DashboardAccountBalancesUseCase constructor(
    private val dashboardRepository: DashboardRepository
) : BaseUseCase<AccountBalancesData, Any?>() {

    override suspend fun run(params: Any?): AccountBalancesData {
        return dashboardRepository.accountBalances(params)
    }
}