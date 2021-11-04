package com.simple.app.ocbc_project_task.features.transfer

import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.simple.app.ocbc_project_task.features.BaseViewModelTests
import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData
import com.simple.app.ocbc_project_task.features.transfer.data.model.TransferAmountData
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferPayeesUseCase
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferUseCase
import com.simple.app.ocbc_project_task.features.transfer.ui.TransferViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.mockito.Mock
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class TransferViewModelTest : BaseViewModelTests<TransferViewModel>() {

    @Mock
    lateinit var transferUseCase: TransferUseCase

    @Mock
    lateinit var transferPayeesUseCase: TransferPayeesUseCase

    @Mock
    private lateinit var payeesObserver: Observer<List<PayeesData.Data>>

    @Mock
    private lateinit var transferObserver: Observer<TransferAmountData.Data>

    override fun createViewModel(): TransferViewModel {
        return TransferViewModel(
            transferUseCase = transferUseCase,
            transferPayeesUseCase = transferPayeesUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should get recipient list`() {
        with(vm) {
            val payeeData = createPayeesData()
            testCoroutineRule.runBlockingTest {
                whenever(transferPayeesUseCase.run()).thenReturn(payeeData)
            }
            initialise()
            payeesResult.observeForever(payeesObserver)
            verify(payeesObserver).onChanged(payeeData?.data)
            payeesResult.removeObserver(payeesObserver)
        }
    }

    @Test
    fun `should make successful transfer`() {
        with(vm) {
            val transferAmountData = createTransferData()
            val transferRequestParams = TransferUseCase.TransferRequestParams(
                "", BigDecimal.ZERO, "", ""
            )
            testCoroutineRule.runBlockingTest {
                whenever(transferUseCase.run(transferRequestParams)).thenReturn(transferAmountData)
            }
            makeTransfer("", BigDecimal.ZERO, "", "")
            transferResult.observeForever(transferObserver)
            verify(transferObserver).onChanged(transferAmountData?.data)
            transferResult.removeObserver(transferObserver)
        }
    }

    private fun createPayeesData(): PayeesData? {
        val gson = Gson()
        val jsonString =
            "{\"status\":\"success\",\"data\":[{\"id\":\"8a6da1a4-6f5f-4b53-9b90-0f8c57661a5d\",\"accountNo\":\"8013-777-3232\",\"accountHolderName\":\"Jason\"},{\"id\":\"19bbc716-ddc3-48d1-a6f9-bb7b96749826\",\"accountNo\":\"4489-991-0023\",\"accountHolderName\":\"Jane\"},{\"id\":\"bcb6f34e-412d-4424-8aa9-59a299f57eb1\",\"accountNo\":\"1234-000-1234\",\"accountHolderName\":\"Max Yee\"},{\"id\":\"0348923c-cb08-4413-8683-5430769a5ee9\",\"accountNo\":\"8182-321-9921\",\"accountHolderName\":\"Daniel\"}]}"
        return gson.fromJson(jsonString, PayeesData::class.java)
    }

    private fun createTransferData(): TransferAmountData? {
        val gson = Gson()
        val jsonString =
            "{\"status\":\"success\",\"data\":{\"id\":\"4a75bc0a-dccd-4d83-b5d8-4a9178894a19\",\"recipientAccountNo\":\"4992-992-9021\",\"amount\":100,\"date\":\"2021-09-12T00:00:00.000Z\",\"description\":\"room rental\"}}"
        return gson.fromJson(jsonString, TransferAmountData::class.java)
    }
}