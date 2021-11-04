package com.simple.app.ocbc_project_task.common.core.interceptors

open class TokenManager : TokenService {
    private var token: TokenResponse? = null

    override fun getToken(): TokenResponse? {
        return this.token
    }

    override fun setToken(token: TokenResponse) {
        this.token = token
    }

    override fun clear() {
        token = null
    }
}