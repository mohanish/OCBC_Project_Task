package com.simple.app.ocbc_project_task.features

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Base class for testing ViewModels.
 *
 * The ViewModel under test will be accessible via the property [vm].
 */
@RunWith(MockitoJUnitRunner::class)
abstract class BaseViewModelTests<VM : ViewModel> : ViewModel() {

    protected lateinit var vm: VM

    @ObsoleteCoroutinesApi
    val mainThreadSurrogate = newSingleThreadContext("UI thread")

    /**
     * This bypasses the main thread check, and immediately runs any tasks on your test thread,
     * allowing for immediate and predictable calls and therefore assertions
     */
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()


    /**
     * Initialize and return the view model under test here.
     */
    abstract fun createViewModel(): VM

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Before
    fun setupViewModel() {
        vm = createViewModel()
    }
}