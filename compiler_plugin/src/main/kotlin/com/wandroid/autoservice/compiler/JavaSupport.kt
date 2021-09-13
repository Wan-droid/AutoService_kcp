package com.wandroid.autoservice.compiler

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.jvm.config.JavaSourceRoot
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager
import org.jetbrains.kotlin.com.intellij.psi.*
import org.jetbrains.kotlin.config.CompilerConfiguration
import java.io.File
import java.nio.file.Files

internal class JavaSupport(private val dataSet: MutableMap<String, MutableSet<String>>) {

    private fun innerCollectAllJavaFiles(
        project: Project,
        compilerConfiguration: CompilerConfiguration
    ): MutableList<PsiJavaFile> {
        val psiManager = PsiManager.getInstance(project)
        val localFileSystem =
            VirtualFileManager.getInstance().getFileSystem(StandardFileSystems.FILE_PROTOCOL)
        val contentRoots = compilerConfiguration[CLIConfigurationKeys.CONTENT_ROOTS] ?: emptyList()
        val javaSourceRoots = mutableListOf<File>()
        javaSourceRoots.addAll(contentRoots.filterIsInstance<JavaSourceRoot>().map { it.file })
        val javaFiles = javaSourceRoots
            .sortedBy { Files.isSymbolicLink(it.toPath()) } // Get non-symbolic paths first
            .flatMap { root -> root.walk().filter { it.isFile && it.extension == "java" }.toList() }
            .sortedBy { Files.isSymbolicLink(it.toPath()) } // This time is for .java files
            .distinctBy { it.canonicalPath }
            .mapNotNull { file ->
                localFileSystem.findFileByPath(file.path)
                    ?.let { psiManager.findFile(it) } as? PsiJavaFile
            }
            .toMutableList()
        return javaFiles
    }

    fun collectAutoServiceAnnotation(
        project: Project,
        compilerConfiguration: CompilerConfiguration
    ) {
        val javaFiles = innerCollectAllJavaFiles(project, compilerConfiguration)

        javaFiles.asSequence()
            .flatMap { psiJavaFile -> psiJavaFile.classes.asSequence() }
            .flatMap { psiClass -> psiClass.annotations.map { psiClass to it }.asSequence() }
            .filter { it.second.qualifiedName == AutoServiceAnalysisExtension.AUTO_SERVICE_NAME }
            .flatMap { pair -> pair.second.parameterList.attributes.map { pair.first to it }.asSequence() }
            .map { it.first to it.second.value as? PsiArrayInitializerMemberValue }
            .filter { it.second != null }
            .flatMap { pair -> pair.second!!.initializers.map { pair.first to it }.asSequence() }
            .forEach { pair ->
                val canonicalName = argToCanonicalName(pair.second)!!
                val valueData = dataSet.getOrElse(canonicalName) {
                    val set = hashSetOf<String>()
                    dataSet[canonicalName] = set
                    set
                }
                //psiFile->psiClass->psiAnnotation->PsiAnnotationParameterList->PsiNameValuePair
                // ->PsiArrayInitializerMemberValue->PsiAnnotationMemberValue
                pair.first.qualifiedName?.let { valueData.add(it) }
            }
    }

    private fun argToCanonicalName(member: PsiAnnotationMemberValue): String? {
        val psiType = JavaPsiFacade.getInstance(member.project).constantEvaluationHelper
            .computeConstantExpression(member) as? PsiType
        return psiType?.canonicalText
    }

}