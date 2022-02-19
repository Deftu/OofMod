package xyz.deftu.oofmod.config

import java.util.function.Consumer
import java.util.regex.Pattern

class RegexPropertyListener(
    private val callback: (Pattern) -> Unit
) : Consumer<String> {
    override fun accept(t: String) {
        callback.invoke(Pattern.compile(t))
    }
}