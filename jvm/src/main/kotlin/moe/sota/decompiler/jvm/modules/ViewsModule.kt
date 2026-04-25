package moe.sota.decompiler.jvm.modules

import moe.sota.decompiler.jvm.models.FileModel
import moe.sota.decompiler.jvm.views.*
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
