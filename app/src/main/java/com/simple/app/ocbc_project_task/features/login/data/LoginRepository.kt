package com.simple.app.ocbc_project_task.features.login.data

import com.simple.app.ocbc_project_task.common.core.interceptors.TokenResponse

interface LoginRepository {

    suspend fun userAuthentication(params: Any?): TokenResponse
}
