package com.simple.app.ocbc_project_task.features.login.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.simple.app.ocbc_project_task.R
import com.simple.app.ocbc_project_task.common.binding.ViewBindingFragment
import com.simple.app.ocbc_project_task.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : ViewBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun initializeLayoutBinding(view: View): FragmentLoginBinding {
        return FragmentLoginBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@LoginFragment).navigateUp();
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, onBackPressedCallback
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText = layout.username
        val passwordEditText = layout.password
        val loginButton = layout.login

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                showBusyProgressDialog(false)
                loginResult ?: return@Observer
                updateUiWithUser()
            })
        loginViewModel.loginError.observe(viewLifecycleOwner,
            Observer { loginResult ->
                showBusyProgressDialog(false)
                loginResult ?: return@Observer
                showLoginFailed(loginResult)
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            showBusyProgressDialog(true)
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }

    private fun updateUiWithUser() {
        Toast.makeText(context, "welcome", Toast.LENGTH_LONG).show()
        val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(context, errorString, Toast.LENGTH_LONG).show()
        val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
        view?.findNavController()?.navigate(action)
    }
}