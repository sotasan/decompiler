package moe.sota.decompiler.jvm.modules

import moe.sota.decompiler.jvm.services.LanguageService
import moe.sota.decompiler.jvm.services.ProcessService
import moe.sota.decompiler.jvm.services.SearchService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val servicesModule = module {
    singleOf(::LanguageService)
    singleOf(::ProcessService)
    singleOf(::SearchService)
}
