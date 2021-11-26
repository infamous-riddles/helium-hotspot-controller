package com.github.infamousriddles.sensecapcontroller.arch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<Params, Type> {
    protected abstract val dispatcher: CoroutineDispatcher

    abstract suspend fun run(params: Params): Type

    suspend operator fun invoke(params: Params): Type = withContext(dispatcher) {
        run(params)
    }
}
