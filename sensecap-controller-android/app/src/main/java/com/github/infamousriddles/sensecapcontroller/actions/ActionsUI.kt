package com.github.infamousriddles.sensecapcontroller.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.infamousriddles.sensecapcontroller.actions.arch.viewmodel.ActionsIntent
import com.github.infamousriddles.sensecapcontroller.actions.arch.viewmodel.ActionsUIEvent
import com.github.infamousriddles.sensecapcontroller.actions.arch.viewmodel.ActionsViewModel
import com.github.infamousriddles.sensecapcontroller.arch.rememberFlowWithLifecycle
import com.github.infamousriddles.sensecapcontroller.ui.theme.SensecapControllerTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Actions(viewModel: ActionsViewModel) {

    val state = viewModel.state.collectAsState()
    val intents = viewModel.uiEvents.rememberFlowWithLifecycle()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(intents, scaffoldState) {
        val snackbarHostState = scaffoldState.snackbarHostState

        intents.collectLatest { intent ->
            when (intent) {
                ActionsUIEvent.FastSyncConfirmed ->
                    snackbarHostState.showSnackbar(message = "FastSync confirmed")
                ActionsUIEvent.ResetBlocksConfirmed ->
                    snackbarHostState.showSnackbar(message = "ResetBlocks confirmed")
                ActionsUIEvent.RebootConfirmed ->
                    snackbarHostState.showSnackbar(message = "Reboot confirmed")
                ActionsUIEvent.ShutdownConfirmed ->
                    snackbarHostState.showSnackbar(message = "Shutdown confirmed")
                is ActionsUIEvent.ShowFailure -> {
                    snackbarHostState.showSnackbar(
                        message = intent.throwable.message ?: "Error",
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "SenseCap Controller") },
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.value.shouldShowLoader) {
                CircularProgressIndicator()
            } else {
                Button(
                    modifier = Modifier
                        .padding(16.dp),
                    onClick = { viewModel.processIntent(intent = ActionsIntent.FastSync) }
                ) {
                    Text(text = "Fast Sync")
                }

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { viewModel.processIntent(intent = ActionsIntent.ResetBlocks) }
                ) {
                    Text(text = "Reset blocks")
                }

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { viewModel.processIntent(intent = ActionsIntent.Reboot) }
                ) {
                    Text(text = "Reboot")
                }

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { viewModel.processIntent(intent = ActionsIntent.Shutdown) }
                ) {
                    Text(text = "Shutdown")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SensecapControllerTheme {
        Actions(ActionsViewModel())
    }
}

