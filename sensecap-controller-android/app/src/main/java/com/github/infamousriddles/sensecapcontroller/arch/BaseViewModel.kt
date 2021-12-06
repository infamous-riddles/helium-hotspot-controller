package com.github.infamousriddles.sensecapcontroller.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Intent, UIEvent>(
    initialState: State
) : ViewModel() {

    protected val loggingExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onException(throwable = throwable)
    }

    private val intents = MutableSharedFlow<Intent>()

    private val _uiEvents = MutableSharedFlow<UIEvent>()
    val uiEvents: SharedFlow<UIEvent>
        get() = _uiEvents

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State>
        get() = _state

    protected val currentState: State
        get() = _state.value

    init {
        intents.asSharedFlow()
            .onEach {
                viewModelScope.launch(loggingExceptionHandler) { handleIntent(it) }
            }
            .launchIn(viewModelScope)
    }

    fun processIntent(intent: Intent) {
        viewModelScope.launch(loggingExceptionHandler) {
            intents.emit(intent)
        }
    }

    protected abstract suspend fun handleIntent(intent: Intent)

    protected fun emitUIEvent(uiEvent: UIEvent) {
        viewModelScope.launch(loggingExceptionHandler) {
            _uiEvents.emit(uiEvent)
        }
    }

    protected fun emitState(state: State) {
        _state.value = state
    }

    protected open fun onException(throwable: Throwable) {
        throwable.printStackTrace()
    }
}

