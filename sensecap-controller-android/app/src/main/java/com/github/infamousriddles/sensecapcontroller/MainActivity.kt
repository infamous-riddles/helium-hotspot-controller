package com.github.infamousriddles.sensecapcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.github.infamousriddles.sensecapcontroller.actions.arch.viewmodel.ActionsViewModel
import com.github.infamousriddles.sensecapcontroller.actions.Actions
import com.github.infamousriddles.sensecapcontroller.ui.theme.SensecapControllerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ActionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensecapControllerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Actions(viewModel = viewModel)
                }
            }
        }
    }
}
