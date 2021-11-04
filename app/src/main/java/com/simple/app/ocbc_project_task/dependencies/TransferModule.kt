package com.simple.app.ocbc_project_task.dependencies

import com.simple.app.ocbc_project_task.features.transfer.data.TransferApi
import com.simple.app.ocbc_project_task.features.transfer.data.TransferRepository
import com.simple.app.ocbc_project_task.features.transfer.data.TransferRepositoryImpl
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferPayeesUseCase
import com.simple.app.ocbc_project_task.features.transfer.data.usecase.TransferUseCase
import com.simple.app.ocbc_project_task.features.transfer.ui.TransferViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val TransferModule = module {
    viewModel { TransferViewModel(get(), get()) }
    single { createTransferRepository(get()) }
    single { createTransferUseCase(get()) }
    single { createPayeesUseCase(get()) }
}

fun createTransferRepository(transferApi: TransferApi): TransferRepository {
    return TransferRepositoryImpl(transferApi)
}

fun createTransferUseCase(transferRepository: TransferRepository): TransferUseCase {
    return TransferUseCase(transferRepository)
}

fun createPayeesUseCase(transferRepository: TransferRepository): TransferPayeesUseCase {
    return TransferPayeesUseCase(transferRepository)
}