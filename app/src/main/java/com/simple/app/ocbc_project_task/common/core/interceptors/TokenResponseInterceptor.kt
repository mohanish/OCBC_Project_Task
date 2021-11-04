package com.simple.app.ocbc_project_task.common.core.interceptors

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * The [TokenResponseInterceptor] intercepts all incoming responses from the API
 * and checks the [accesstoken] if available and store them in Token Manager.
 *
 */
class TokenResponseInterceptor @Inject constructor(
    private val gson: Gson,
    private val tokenService: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val tokenResponse = parseForToken(response.peekBody(Long.MAX_VALUE))

        if (tokenResponse?.token == null) {
            return response
        }

        tokenService.setToken(tokenResponse)

        return response
    }

    /**
     * Maps the response body to an [TokenResponse] object. Returns `null` if it is
     * unable to perform the mapping.
     */
    private fun parseForToken(body: ResponseBody?): TokenResponse? {
        if (body == null) return null
        return try {
            this.gson.fromJson(body.string(), TokenResponse::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }
}
