package com.wandroid.autoservice.compiler

import com.wandroid.autoservice.BuildConfig
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension


class AutoServiceCLiProcessor : CommandLineProcessor {
    override val pluginId = BuildConfig.PLUGIN_ID
    override val pluginOptions: Collection<AbstractCliOption> = emptyList()
}


class AutoServiceComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {

        AnalysisHandlerExtension.registerExtension(
            project,
            AutoServiceAnalysisExtension(configuration)
        )
    }
}

//TODO:Kotlin支持内部类
//TODO:Java支持静态内部类