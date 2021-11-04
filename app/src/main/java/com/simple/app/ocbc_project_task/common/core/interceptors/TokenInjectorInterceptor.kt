package com.simple.app.ocbc_project_task.common.core.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * [TokenInjectorInterceptor] injects access tokens to outgoing requests.
 *
 * You must supply it with an implementation of [TokenService] for it to
 * retrieve the appropriate tokens.
 */
class TokenInjectorInterceptor @Inject constructor(
    private val tokenService: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val token = tokenService.getToken() ?: return chain.proceed(request)

        return chain.proceed(request.injectAuthorizationHeader(token.token))
    }

    private fun Request.injectAuthorizationHeader(header: String) = newBuilder()
        .addHeader("Token", header)
        .addHeader("Authorization", header)
        .build()
}
