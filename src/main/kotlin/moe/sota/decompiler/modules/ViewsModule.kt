package moe.sota.decompiler.modules

import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.views.AboutView
import moe.sota.decompiler.views.StartView
import moe.sota.decompiler.views.TabView
import moe.sota.decompiler.views.TabsView
import moe.sota.decompiler.views.TreeView
import moe.sota.decompiler.views.WindowView
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
