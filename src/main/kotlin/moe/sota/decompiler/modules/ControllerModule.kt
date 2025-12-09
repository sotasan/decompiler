package moe.sota.decompiler.modules

import moe.sota.decompiler.controllers.WindowController
import org.koin.dsl.module

val controllerModule = module {
    single { WindowController }
}
