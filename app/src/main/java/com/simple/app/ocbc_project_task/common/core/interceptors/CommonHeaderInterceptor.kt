package com.simple.app.ocbc_project_task.common.core.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * The [CommonHeaderInterceptor] adds the common headers required in all
 * outgoing requests.
 */
class CommonHeaderInterceptor @Inject constructor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .appendContentType()
            .acceptType()
            .build()
        return chain.proceed(newRequest)
    }

    private fun Request.Builder.appendContentType(): Request.Builder {
        return this.header("Content-Type", "application/json")
    }

    private fun Request.Builder.acceptType(): Request.Builder {
        return this.header("Accept", "application/json")
    }
}
