package com.simple.app.ocbc_project_task.dependencies

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simple.app.ocbc_project_task.common.core.interceptors.CommonHeaderInterceptor
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenInjectorInterceptor
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenManager
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenResponseInterceptor
import com.simple.app.ocbc_project_task.features.dashboard.data.DashboardApi
import com.simple.app.ocbc_project_task.features.login.data.LoginApi
import com.simple.app.ocbc_project_task.features.transfer.data.TransferApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

val NetworkModule = module {
    single { createGson() }
    single { createTokenService() }
    single { createLoginService(get()) }
    single { createDashboardService(get()) }
    single { createTransferService(get()) }
    single { createRetrofit(get(), get()) }
    single {
        createOkHttpClient(
            createChuckerInterceptor(get()), createCommonHeadersInterceptor(),
            createTokenInjectorInterceptor(get()), createTokenResponseInterceptor(get(), get())
        )
    }
}

fun createGson(): Gson {
    return GsonBuilder().create()
}

fun createChuckerInterceptor(context: Context): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(context = context)
        .collector(collector = ChuckerCollector(context = context, showNotification = true))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()
}

fun createCommonHeadersInterceptor(): CommonHeaderInterceptor {
    return CommonHeaderInterceptor()
}

fun createTokenInjectorInterceptor(service: TokenManager): TokenInjectorInterceptor {
    return TokenInjectorInterceptor(service)
}

fun createTokenResponseInterceptor(gson: Gson, service: TokenManager): TokenResponseInterceptor {
    return TokenResponseInterceptor(gson, service)
}

fun createTokenService(): TokenManager {
    return TokenManager()
}

fun createOkHttpClient(
    chuckerInterceptor: ChuckerInterceptor,
    addCommonHeaders: CommonHeaderInterceptor,
    injectToken: TokenInjectorInterceptor,
    storeIncomingTokens: TokenResponseInterceptor,
): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val builder = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(addCommonHeaders)
        .addInterceptor(injectToken)
        .addInterceptor(storeIncomingTokens)
        .addInterceptor(chuckerInterceptor)

    return builder.build()
}

fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://localhost:4000/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun createLoginService(retrofit: Retrofit): LoginApi {
    return retrofit.create(LoginApi::class.java)
}

fun createDashboardService(retrofit: Retrofit): DashboardApi {
    return retrofit.create(DashboardApi::class.java)
}

fun createTransferService(retrofit: Retrofit): TransferApi {
    return retrofit.create(TransferApi::class.java)
}