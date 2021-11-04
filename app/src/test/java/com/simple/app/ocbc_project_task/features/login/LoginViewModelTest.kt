package com.simple.app.ocbc_project_task.features.login

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenManager
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenResponse
import com.simple.app.ocbc_project_task.features.BaseViewModelTests
import com.simple.app.ocbc_project_task.features.login.data.usecase.AuthenticationUseCase
import com.simple.app.ocbc_project_task.features.login.ui.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class LoginViewModelTest : BaseViewModelTests<LoginViewModel>() {

    @Mock
    lateinit var authenticationUseCase: AuthenticationUseCase

    @Mock
    lateinit var tokenService: TokenManager

    @Mock
    private lateinit var loginSuccessObserver: Observer<TokenResponse>

    override fun createViewModel(): LoginViewModel {
        return LoginViewModel(
            authenticationUseCase = authenticationUseCase,
            tokenService = tokenService
        )
    }

    @Test
    fun `should login success when valid input`() {
        val tokenResponse = TokenResponse(status = "success", token = "1234567890")
        val loginRequestParams = AuthenticationUseCase.LoginRequestParams("ocbc", "123456")
        testCoroutineRule.runBlockingTest {
            whenever(authenticationUseCase.run(loginRequestParams)).thenReturn(tokenResponse)
        }
        vm.login("ocbc", "123456")
        vm.loginResult.observeForever(loginSuccessObserver)
        verify(loginSuccessObserver).onChanged(tokenResponse)
        vm.loginResult.removeObserver(loginSuccessObserver)
    }
}