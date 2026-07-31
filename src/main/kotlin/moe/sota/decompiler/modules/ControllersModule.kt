package moe.sota.decompiler.modules

import moe.sota.decompiler.controllers.AboutController
import moe.sota.decompiler.controllers.StartController
import moe.sota.decompiler.controllers.TabController
import moe.sota.decompiler.controllers.TabsController
import moe.sota.decompiler.controllers.TreeController
import moe.sota.decompiler.controllers.WindowController
import moe.sota.decompiler.models.FileModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val controllersModule = module {
    singleOf(::AboutController)
    singleOf(::StartController)
    singleOf(::TreeController)
    singleOf(::WindowController)

    single { TabsController(get()) { fileModel -> get { parametersOf(fileModel) } } }

    factory { (fileModel: FileModel) ->
        TabController(fileModel, get { parametersOf(fileModel) }, get())
    }
}
