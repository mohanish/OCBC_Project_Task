package com.simple.app.ocbc_project_task.features.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.app.ocbc_project_task.R
import com.simple.app.ocbc_project_task.common.core.interceptors.TokenResponse
import com.simple.app.ocbc_project_task.common.domain.exception.ApiError
import com.simple.app.ocbc_project_task.common.domain.usecase.BaseUsecaseResponse
import com.simple.app.ocbc_project_task.features.login.data.usecase.AuthenticationUseCase

class LoginViewModel(private val authenticationUseCase: AuthenticationUseCase) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    private val _loginResult = MutableLiveData<TokenResponse>()
    val loginResult: LiveData<TokenResponse> = _loginResult

    fun login(username: String, password: String) {
        val loginRequestParams = AuthenticationUseCase.LoginRequestParams(username, password)
        authenticationUseCase.invoke(
            viewModelScope, loginRequestParams,
            object : BaseUsecaseResponse<TokenResponse> {
                override fun onSuccess(trackerData: TokenResponse) {
                    _loginResult.value = trackerData
                }

                override fun onError(apiError: ApiError?) {
                    _loginError.value = apiError?.getErrorMessage()
                }
            }
        )
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}