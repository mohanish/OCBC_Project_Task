package com.simple.app.ocbc_project_task.dependencies

import com.simple.app.ocbc_project_task.features.login.data.LoginApi
import com.simple.app.ocbc_project_task.features.login.data.LoginRepository
import com.simple.app.ocbc_project_task.features.login.data.LoginRepositoryImpl
import com.simple.app.ocbc_project_task.features.login.data.usecase.AuthenticationUseCase
import com.simple.app.ocbc_project_task.features.login.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val LoginModule = module {
    viewModel { LoginViewModel(get()) }
    single { createLoginRepository(get()) }
    single { createLoginUseCase(get()) }
}

fun createLoginRepository(loginApi: LoginApi): LoginRepository {
    return LoginRepositoryImpl(loginApi)
}

fun createLoginUseCase(loginRepository: LoginRepository): AuthenticationUseCase {
    return AuthenticationUseCase(loginRepository)
}