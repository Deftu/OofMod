package xyz.deftu.oofmod.config

import java.util.function.Consumer
import java.util.regex.Pattern
import kotlin.reflect.KProperty

class RegexPropertyListener(
    private val property: KProperty<Pattern>
) : Consumer<String> {
    override fun accept(value: String) {
        try {
            val regex = Pattern.compile(value)
            property.call(regex)
        } catch (_: Exception) {
            // Ignore it. We check this every couple seconds anyway.
        }
    }
}
