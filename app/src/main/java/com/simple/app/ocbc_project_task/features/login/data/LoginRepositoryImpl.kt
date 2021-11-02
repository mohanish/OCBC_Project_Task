package com.simple.app.ocbc_project_task.features.login.data

import com.simple.app.ocbc_project_task.common.core.interceptors.TokenResponse
import com.simple.app.ocbc_project_task.features.login.data.usecase.AuthenticationUseCase

class LoginRepositoryImpl(private val loginApi: LoginApi) : LoginRepository {
    override suspend fun userAuthentication(params: Any?): TokenResponse {
        val loginRequestParams = params as AuthenticationUseCase.LoginRequestParams
        return loginApi.authentication(loginRequestParams)
    }
}