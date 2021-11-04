package com.simple.app.ocbc_project_task.features

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
abstract class BaseViewModelTests<VM : ViewModel> : ViewModel() {

    protected lateinit var vm: VM

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    /**
     * Initialize and return the view model under test here.
     */
    abstract fun createViewModel(): VM

    @Before
    fun setupViewModel() {
        vm = createViewModel()
    }
}