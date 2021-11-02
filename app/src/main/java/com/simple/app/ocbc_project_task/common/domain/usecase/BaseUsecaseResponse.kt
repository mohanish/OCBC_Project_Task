package com.simple.app.ocbc_project_task.common.domain.usecase

import com.simple.app.ocbc_project_task.common.domain.exception.ApiError

interface BaseUsecaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}