package com.github.infamousriddles.sensecapcontroller.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.rememberFlowWithLifecycle(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(this, lifecycle, minActiveState) {
    flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}
