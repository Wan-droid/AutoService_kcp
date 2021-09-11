package com.wandroid.autoservice.compiler

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.container.ComponentProvider
import org.jetbrains.kotlin.container.get
import org.jetbrains.kotlin.context.ProjectContext
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.extensions.AnnotationBasedExtension
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtModifierListOwner
import org.jetbrains.kotlin.psi.KtVisitorVoid
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.constants.ArrayValue
import org.jetbrains.kotlin.resolve.constants.KClassValue
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension
import org.jetbrains.kotlin.resolve.lazy.ResolveSession
import java.io.File

class AutoServiceAnalysisExtension(
    private val compilerConfiguration: CompilerConfiguration
) : AnalysisHandlerExtension, AnnotationBasedExtension {
    companion object {
        internal val AUTO_SERVICE_NAME = AutoService::class.java.name
        internal const val SERVICES_DIR = "META-INF/services/"
    }

    private val outputDir = compilerConfiguration[JVMConfigurationKeys.OUTPUT_DIRECTORY]!!

    private val dataSet: MutableMap<String, MutableSet<String>> = linkedMapOf()

    override fun doAnalysis(
        project: Project,
        module: ModuleDescriptor,
        projectContext: ProjectContext,
        files: Collection<KtFile>,
        bindingTrace: BindingTrace,
        componentProvider: ComponentProvider
    ): AnalysisResult? {
        println("TempAnalysisExtension doAnalysis:")
        val kv = KVisitor(bindingTrace, componentProvider)
        files.forEach { ktFile ->
            ktFile.accept(kv)
        }
        return null
    }

    override fun analysisCompleted(
        project: Project,
        module: ModuleDescriptor,
        bindingTrace: BindingTrace,
        files: Collection<KtFile>
    ): AnalysisResult? {
        println("00000000000000" + dataSet)
        val checkDir = checkParentDir()
        if (checkDir) {
            dataSet.asSequence()
                .map { File(outputDir, SERVICES_DIR + it.key) to it.value }
                .filter { it.first.exists() || it.first.createNewFile() }
                .filter { it.first.canWrite() || it.first.setWritable(true) }
                .forEach { pair ->
                    val writeText = StringBuilder()
                    pair.second.asSequence().forEach { writeText.append(it).appendLine() }
                    writeText.setLength(writeText.length - 1)
                    pair.first.writeText(writeText.toString())
                }
        }
        return super.analysisCompleted(project, module, bindingTrace, files)
    }

    private fun checkParentDir(): Boolean {
        val parentFile = File(outputDir, SERVICES_DIR)
        if (!parentFile.exists()) {
            return parentFile.mkdirs()
        }
        return true
    }

    private inner class KVisitor(
        private val bindingTrace: BindingTrace,
        componentProvider: ComponentProvider
    ) : KtVisitorVoid() {

        private val autoServiceFqName = FqName(AUTO_SERVICE_NAME)
        private val autoServiceParameterName = Name.identifier("value")
        private val resolveSession = componentProvider.get<ResolveSession>()

        override fun visitKtFile(file: KtFile) {
            super.visitKtFile(file)
            file.declarations.forEach { it.accept(this) }
        }

        override fun visitClass(klass: KtClass) {
            super.visitClass(klass)
            val resolveToDescriptor = resolveSession.resolveToDescriptor(klass)
            val hasSpecialAnnotation = resolveToDescriptor.hasSpecialAnnotation(klass)
            println("visitClass:${klass.annotationEntries.size}===$hasSpecialAnnotation")

            if (hasSpecialAnnotation) {
                val autoServiceAnnotation =
                    resolveToDescriptor.annotations.findAnnotation(autoServiceFqName)!!
                val constantValue =
                    autoServiceAnnotation.allValueArguments[autoServiceParameterName]
                constantValue?.accept(AnnotationArgsVisitor(), klass)
            }

        }
    }

    override fun getAnnotationFqNames(modifierListOwner: KtModifierListOwner?): List<String> {
        return mutableListOf(AUTO_SERVICE_NAME)
    }


    private inner class AnnotationArgsVisitor : AbsAnnotationArgsVisitor() {

        override fun visitArrayValue(value: ArrayValue?, data: KtClass?) {
            value?.value?.forEach { it.accept(this, data) }
        }

        override fun visitKClassValue(value: KClassValue?, data: KtClass?) {
            if (value == null) return
            if (data == null) return
            when (val classValue = value.value) {
                is KClassValue.Value.NormalClass -> {
                    val classId = classValue.value.classId
                    val inRootPackage = classId.packageFqName.isRoot
                    val key = if (inRootPackage) {
                        classId.relativeClassName.asString()
                    } else {
                        classId.packageFqName.asString() + "." + classId.relativeClassName.asString()
                    }
                    val valueData = dataSet.getOrElse(key) {
                        val set = hashSetOf<String>()
                        dataSet[key] = set
                        set
                    }
                    data.fqName?.asString()?.let { valueData.add(it) }
                }
                is KClassValue.Value.LocalClass -> {
                }
            }


        }
    }
}




