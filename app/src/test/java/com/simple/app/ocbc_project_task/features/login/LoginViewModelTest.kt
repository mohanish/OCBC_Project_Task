package com.simple.app.ocbc_project_task.features.login

import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenResponse
import com.simple.app.ocbc_project_task.features.BaseViewModelTests
import com.simple.app.ocbc_project_task.features.login.data.usecase.AuthenticationUseCase
import com.simple.app.ocbc_project_task.features.login.ui.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest : BaseViewModelTests<LoginViewModel>() {

    @Mock
    lateinit var authenticationUseCase: AuthenticationUseCase

    override fun createViewModel(): LoginViewModel {
        return LoginViewModel(
            authenticationUseCase = authenticationUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should get recipient list`() = runBlockingTest {
        with(vm) {
            whenever(authenticationUseCase.run()).thenReturn(TokenResponse(""))
            login("ocbc", "123456")
            Truth.assertThat(loginResult.value).isEqualTo(any())
        }
    }
}