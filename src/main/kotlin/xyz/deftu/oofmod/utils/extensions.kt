package xyz.deftu.oofmod.utils

import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

fun List<Pattern>.checkEach(input: CharSequence): Matcher {
    for (pattern in this) {
        val matcher = pattern.matcher(input)
        if (matcher.find()) return matcher
    }

    return first().matcher(input)
}