package moe.sota.decompiler.modules

import moe.sota.decompiler.controllers.*
import moe.sota.decompiler.models.FileModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val controllersModule = module {
    singleOf(::AboutController)
    singleOf(::TreeController)
    singleOf(::StartController)
    singleOf(::WindowController)

    single { TabsController(get()) { fileModel -> get { parametersOf(fileModel) } } }

    factory { (fileModel: FileModel) ->
        TabController(fileModel, get { parametersOf(fileModel) }, get())
    }
}
