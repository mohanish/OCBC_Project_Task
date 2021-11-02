package com.simple.app.ocbc_project_task.features.login.data

import com.simple.app.ocbc_project_task.common.core.interceptors.TokenResponse
import com.simple.app.ocbc_project_task.features.login.data.usecase.AuthenticationUseCase
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/authenticate/login")
    suspend fun authentication(@Body loginRequest: AuthenticationUseCase.LoginRequestParams): TokenResponse
}