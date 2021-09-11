package com.wandroid.autoservice.compiler

import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.resolve.constants.*

internal open class AbsAnnotationArgsVisitor : AnnotationArgumentVisitor<Unit, KtClass?> {
    override fun visitLongValue(value: LongValue, data: KtClass?) {

    }

    override fun visitIntValue(value: IntValue?, data: KtClass?) {

    }

    override fun visitErrorValue(value: ErrorValue?, data: KtClass?) {

    }

    override fun visitShortValue(value: ShortValue?, data: KtClass?) {

    }

    override fun visitByteValue(value: ByteValue?, data: KtClass?) {

    }

    override fun visitDoubleValue(value: DoubleValue?, data: KtClass?) {

    }

    override fun visitFloatValue(value: FloatValue?, data: KtClass?) {

    }

    override fun visitBooleanValue(value: BooleanValue?, data: KtClass?) {

    }

    override fun visitCharValue(value: CharValue?, data: KtClass?) {

    }

    override fun visitStringValue(value: StringValue?, data: KtClass?) {

    }

    override fun visitNullValue(value: NullValue?, data: KtClass?) {

    }

    override fun visitEnumValue(value: EnumValue?, data: KtClass?) {

    }

    override fun visitArrayValue(value: ArrayValue?, data: KtClass?) {

    }

    override fun visitAnnotationValue(value: AnnotationValue?, data: KtClass?) {

    }

    override fun visitKClassValue(value: KClassValue?, data: KtClass?) {

    }

    override fun visitUByteValue(value: UByteValue?, data: KtClass?) {

    }

    override fun visitUShortValue(value: UShortValue?, data: KtClass?) {

    }

    override fun visitUIntValue(value: UIntValue?, data: KtClass?) {

    }

    override fun visitULongValue(value: ULongValue?, data: KtClass?) {

    }
}