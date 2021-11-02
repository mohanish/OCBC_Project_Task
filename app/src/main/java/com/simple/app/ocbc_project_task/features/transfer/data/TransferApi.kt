package com.simple.app.ocbc_project_task.features.transfer.data

import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData
import com.simple.app.ocbc_project_task.features.transfer.data.model.TransferAmountData
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferUseCase
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransferApi {

    @POST("/transfer")
    suspend fun makeTransfer(@Body transferRequestParams: TransferUseCase.TransferRequestParams): TransferAmountData

    @GET("/account/payees")
    suspend fun getPayees(): PayeesData

}