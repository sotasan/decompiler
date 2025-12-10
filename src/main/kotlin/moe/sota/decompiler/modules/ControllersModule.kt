package moe.sota.decompiler.modules

import moe.sota.decompiler.controllers.StartController
import moe.sota.decompiler.controllers.WindowController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val controllersModule = module {
    singleOf(::StartController)
    single { WindowController }
}
