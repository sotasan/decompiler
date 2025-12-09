package moe.sota.decompiler.modules

import moe.sota.decompiler.views.StartView
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModule = module {
    singleOf(::StartView)
}
