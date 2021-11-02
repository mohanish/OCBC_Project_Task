package com.simple.app.ocbc_project_task.common.core.interceptors

class TokenManager : TokenService {
    private var token: TokenResponse? = null

    override fun getToken(): TokenResponse? {
        return this.token
    }

    override fun setToken(token: TokenResponse) {
        clear()
        this.token = token
    }

    override fun clear() {
        token = null
    }
}