/*
 * Copyright (C) 2020 Brian Norman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wandroid.autoservice.compiler

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.wandroid.autoservice.compiler.AutoServiceComponentRegistrar
import kotlin.test.assertEquals
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.JvmTarget
import org.junit.Test
import java.io.File


class IrPluginTest {

    @Test
    fun testCompiler() {
        val parent = "src/test/kotlin/com/wandroid/autoservice/compiler"
        val fromPath2 = SourceFile.fromPath(File(parent, "AAAAA.kt"))
        val fromPath = SourceFile.fromPath(File(parent, "TestClass.kt"))
        val fromPath3 =
            SourceFile.fromPath(File(parent, "JavaTest.java"))
        val compileResult = compile(mutableListOf(fromPath, fromPath2, fromPath3))
        assertEquals(KotlinCompilation.ExitCode.OK, compileResult.exitCode)
//        assertThat(kClazz).hasDeclaredMethods("foo")
    }
}

fun compile(
    sourceFiles: List<SourceFile>,
    plugin: ComponentRegistrar = AutoServiceComponentRegistrar(),
): KotlinCompilation.Result {
    return KotlinCompilation().apply {
        sources = sourceFiles
        useIR = true
        jvmTarget = JvmTarget.JVM_1_8.description
        compilerPlugins = listOf(plugin)
        inheritClassPath = true
        workingDir = File("src/test/output")
    }.compile()
}

fun compile(
    sourceFile: SourceFile,
    plugin: ComponentRegistrar = AutoServiceComponentRegistrar(),
): KotlinCompilation.Result {
    return compile(listOf(sourceFile), plugin)
}
