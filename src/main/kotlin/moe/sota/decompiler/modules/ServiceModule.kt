package moe.sota.decompiler.modules

import moe.sota.decompiler.services.LanguageService
import moe.sota.decompiler.services.ProcessService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serviceModule = module {
    singleOf(::LanguageService)
    singleOf(::ProcessService)
}
