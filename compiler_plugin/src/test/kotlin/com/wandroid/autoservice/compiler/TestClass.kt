package com.wandroid.autoservice.compiler
import com.google.auto.service.UUUUU

interface IIIIIII{}


@com.google.auto.service.AutoService(IIIIIII::class, UUUUU::class)
class TestClass {

    fun function0():String {
        println("function0")
        return ""
    }

    fun function1() :String { print(defineStr())
        return ""
    }

    private fun defineStr() = "define String."

}
@com.google.auto.service.AutoService(IIIIIII::class, UUUUU::class)
class TestClass222 {

    fun function0():String {
        println("function0")
        return ""
    }

    fun function1() :String { print(defineStr())
        return ""
    }

    private fun defineStr() = "define String."

}
fun main() {
    val testClass = TestClass()
    println(testClass.function1())
}