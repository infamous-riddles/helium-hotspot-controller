package com.github.infamousriddles.sensecapcontroller.actions.arch.viewmodel

sealed class ActionsUIEvent {
    object ShutdownConfirmed : ActionsUIEvent()
    object RebootConfirmed : ActionsUIEvent()
    object ResetBlocksConfirmed : ActionsUIEvent()
    object FastSyncConfirmed : ActionsUIEvent()
    data class ShowFailure(val throwable: Throwable): ActionsUIEvent()
}

