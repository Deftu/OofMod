package xyz.deftu.oofmod.config

enum class RegexMode(
    val mode: String,
    val allowsKill: Boolean = true,
    val allowsDeath: Boolean = true,
    val regexes: List<Regex> = listOf()
) {
    NONE("None"),
    HYPIXEL("Hypixel", false, false, listOf(
        "(?<username>\\w{1,16}).+ (by|of|to|for|with|the) (?:(?<killer>\\w{1,16}))".toRegex()
    ))
}
