package com.simple.app.ocbc_project_task.features.transfer

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import com.simple.app.ocbc_project_task.features.BaseViewModelTests
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferPayeesUseCase
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferUseCase
import com.simple.app.ocbc_project_task.features.transfer.ui.TransferViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class TransferViewModelTest : BaseViewModelTests<TransferViewModel>() {

    @Mock
    lateinit var transferUseCase: TransferUseCase

    @Mock
    lateinit var transferPayeesUseCase: TransferPayeesUseCase

    override fun createViewModel(): TransferViewModel {
        return TransferViewModel(
            transferUseCase = transferUseCase,
            transferPayeesUseCase = transferPayeesUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should get recipient list`() = runBlockingTest {
        with(vm) {
            whenever(transferPayeesUseCase.run()).thenReturn(createPayeesData())
            getPayees()
            assertThat(payeesResult.value).isEqualTo(createPayeesData()?.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should make transfer`() = runBlockingTest {
        with(vm) {
            whenever(transferUseCase.run()).thenReturn(createTransferData())
            makeTransfer(
                recipientAccountNo = "",
                amount = BigDecimal.ZERO,
                date = "",
                description = "transfer urgently"
            )
            assertThat(transferResult.value).isEqualTo(createTransferData()?.data)
        }
    }
}