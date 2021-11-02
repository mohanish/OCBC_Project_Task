package com.simple.app.ocbc_project_task.features.login.data.usecase

import com.simple.app.ocbc_project_task.common.core.interceptors.TokenResponse
import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUseCase
import com.simple.app.ocbc_project_task.features.login.data.LoginRepository

open class AuthenticationUseCase constructor(
    private val loginRepository: LoginRepository,
) : BaseUseCase<TokenResponse, Any?>() {

    override suspend fun run(params: Any?): TokenResponse {
        return loginRepository.userAuthentication(params)
    }

    data class LoginRequestParams(val username: String, val password: String)
}