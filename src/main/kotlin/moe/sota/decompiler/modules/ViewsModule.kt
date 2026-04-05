package moe.sota.decompiler.modules

import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.views.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewsModule = module {
    singleOf(::AboutView)
    singleOf(::StartView)
    singleOf(::TabsView)
    singleOf(::TreeView)
    singleOf(::WindowView)

    factory { (fileModel: FileModel) -> TabView(fileModel) }
}
