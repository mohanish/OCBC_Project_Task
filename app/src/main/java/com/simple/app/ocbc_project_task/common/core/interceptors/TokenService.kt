package com.simple.app.ocbc_project_task.common.core.interceptors

interface TokenService {

    fun getToken(): TokenResponse?
    fun setToken(token: TokenResponse)
    fun clear()
}
