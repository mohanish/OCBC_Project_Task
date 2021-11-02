package com.simple.app.ocbc_project_task.common.domain.usecase

import com.simple.app.ocbc_project_task.common.domain.exception.traceErrorException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

abstract class BaseUseCase<Type, in Params>() where Type : Any {

    abstract suspend fun run(params: Params? = null): Type

    fun invoke(scope: CoroutineScope, params: Params?, onResult: BaseUsecaseResponse<Type>?) {

        scope.launch {
            try {
                val result = run(params)
                onResult?.onSuccess(result)
            } catch (e: CancellationException) {
                e.printStackTrace()
                onResult?.onError(traceErrorException(e))
            } catch (e: Exception) {
                e.printStackTrace()
                onResult?.onError(traceErrorException(e))
            }
        }
    }
}