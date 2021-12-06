package com.github.infamousriddles.sensecapcontroller.actions.arch.viewmodel

sealed class ActionsIntent {
    object FastSync : ActionsIntent()
    object ResetBlocks : ActionsIntent()
    object Reboot : ActionsIntent()
    object Shutdown : ActionsIntent()
}
