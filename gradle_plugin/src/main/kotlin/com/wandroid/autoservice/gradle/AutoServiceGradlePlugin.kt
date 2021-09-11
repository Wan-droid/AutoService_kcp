package com.wandroid.autoservice.gradle

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

const val pluginId = "com.wandroid.autoservice"
const val groupId = "com.wandroid.autoservice"
const val artifactId = "autoservice_kcp"
const val version = "0.0.1"

class AutoServiceGradlePlugin : KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        return project.provider { emptyList() }
    }

    override fun getCompilerPluginId() = pluginId

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = groupId,
        artifactId = artifactId,
        version = version
    )

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = true
}