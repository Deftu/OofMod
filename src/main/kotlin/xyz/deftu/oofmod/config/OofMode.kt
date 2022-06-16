package xyz.deftu.oofmod.config

object OofMode {
    var regexMode: RegexMode
        get() = RegexMode.values()[OofModConfig.regexMode]
        set(value) {
            OofModConfig.regexMode = value.ordinal
        }
    var allowsTraditionalKill: Boolean
        get() = allowsTraditionalKill && regexMode.allowsKill
        set(value) {
            OofModConfig.allowsTraditionalKill = value
        }
    var allowsTraditionalDeath: Boolean
        get() = allowsTraditionalDeath && regexMode.allowsDeath
        set(value) {
            OofModConfig.allowsTraditionalDeath = value
        }
}
