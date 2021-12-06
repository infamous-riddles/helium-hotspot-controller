package com.github.infamousriddles.sensecapcontroller.actions.arch.viewmodel

import com.github.infamousriddles.sensecapcontroller.actions.arch.usecase.FastSyncUseCase
import com.github.infamousriddles.sensecapcontroller.actions.arch.usecase.RebootUseCase
import com.github.infamousriddles.sensecapcontroller.actions.arch.usecase.ResetBlocksUseCase
import com.github.infamousriddles.sensecapcontroller.actions.arch.usecase.ShutdownUseCase
import com.github.infamousriddles.sensecapcontroller.arch.BaseViewModel

class ActionsViewModel(
    private val fastSyncUseCase: FastSyncUseCase = FastSyncUseCase(),
    private val rebootUseCase: RebootUseCase = RebootUseCase(),
    private val resetBlocksUseCase: ResetBlocksUseCase = ResetBlocksUseCase(),
    private val shutdownUseCase: ShutdownUseCase = ShutdownUseCase()
) : BaseViewModel<ActionState, ActionsIntent, ActionsUIEvent>(
    initialState = ActionState(shouldShowLoader = false)
) {

    override suspend fun handleIntent(intent: ActionsIntent) = when (intent) {
        ActionsIntent.FastSync -> handleFastSync()
        ActionsIntent.ResetBlocks -> handleResetBlocks()
        ActionsIntent.Reboot -> handleReboot()
        ActionsIntent.Shutdown -> handleShutdown()
    }

    private suspend fun handleFastSync() {
        emitState(state = currentState.copy(shouldShowLoader = true))
        fastSyncUseCase(params = Unit)
        emitState(state = currentState.copy(shouldShowLoader = false))
        emitUIEvent(uiEvent = ActionsUIEvent.FastSyncConfirmed)
    }

    private suspend fun handleResetBlocks() {
        emitState(state = currentState.copy(shouldShowLoader = true))
        resetBlocksUseCase(params = Unit)
        emitState(state = currentState.copy(shouldShowLoader = false))
        emitUIEvent(uiEvent = ActionsUIEvent.ResetBlocksConfirmed)
    }

    private suspend fun handleReboot() {
        emitState(state = currentState.copy(shouldShowLoader = true))
        rebootUseCase(params = Unit)
        emitState(state = currentState.copy(shouldShowLoader = false))
        emitUIEvent(uiEvent = ActionsUIEvent.RebootConfirmed)
    }

    private suspend fun handleShutdown() {
        emitState(state = currentState.copy(shouldShowLoader = true))
        shutdownUseCase(params = Unit)
        emitState(state = currentState.copy(shouldShowLoader = false))
        emitUIEvent(uiEvent = ActionsUIEvent.ShutdownConfirmed)
    }

    override fun onException(throwable: Throwable) {
        super.onException(throwable)
        emitState(state = currentState.copy(shouldShowLoader = false))
        emitUIEvent(uiEvent = ActionsUIEvent.ShowFailure(throwable))
    }
}
