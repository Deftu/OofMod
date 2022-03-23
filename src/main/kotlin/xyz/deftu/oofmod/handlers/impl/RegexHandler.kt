package xyz.deftu.oofmod.handlers.impl

import com.google.common.collect.ArrayListMultimap
import com.google.gson.*
import org.apache.commons.io.IOUtils
import xyz.deftu.oofmod.handlers.BaseHandler
import xyz.deftu.oofmod.utils.*
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.regex.Pattern
import kotlin.io.path.readText

object RegexHandler : BaseHandler, Thread("OoMod Regex Thread") {
    @JvmStatic val cache = RegexCache()
    private val jsonParser = JsonParser()
    private var started = false
    private lateinit var path: Path

    fun start(path: Path) {
        this.path = path
        this.started = true
    }

    override fun run() {
        if (!started) throw IllegalStateException("The regex thread has not been started properly yet. Please start it, and do not call the run method directly!")
        execute(path)
    }

    override fun execute(vararg params: Any) {
        val path = params[0]
        if (path !is Path) throw IllegalArgumentException("The only allowed param for the regex handler is the path param.")
        val raw = jsonParser.parse(path.readText(StandardCharsets.UTF_8))
        if (!raw.isJsonObject) throw IllegalStateException("Regex parameter must be a JSON object!")
        val json = raw.asJsonObject
        for (entry in json.entrySet()) recurseCache(entry.key, entry.value)
    }

    private fun recurseCache(key: String, value: JsonElement) {
        if (value is JsonObject) value.entrySet().forEach { recurseCache(key, it.value) }.also { return }
        if (value is JsonArray) value.forEach { recurseCache(key, it) }.also { return }
        if (value is JsonPrimitive) {
            cache.getOrCache(key, value.asString)
        }
    }
}

class RegexCache {
    private val map = ArrayListMultimap.create<String, Pattern>()
    @JvmOverloads fun getOrCache(namespace: String, regex: String? = null): List<Pattern> {
        if (regex != null && !map.containsValue(regex)) map.put(namespace, Pattern.compile(regex))
        return map[namespace]!!
    }
}