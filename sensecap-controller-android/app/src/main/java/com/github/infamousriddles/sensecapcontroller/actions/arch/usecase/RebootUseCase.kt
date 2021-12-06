package com.github.infamousriddles.sensecapcontroller.actions.arch.usecase

import com.github.infamousriddles.sensecapcontroller.arch.BaseUseCase
import com.github.infamousriddles.sensecapcontroller.network.Network
import com.github.infamousriddles.sensecapcontroller.network.SenseCAPService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class RebootUseCase(
    private val senseCAPService: SenseCAPService = Network.senseCapService()
): BaseUseCase<Unit, Unit>() {

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override suspend fun run(params: Unit) {
        senseCAPService.reboot()
    }
}
