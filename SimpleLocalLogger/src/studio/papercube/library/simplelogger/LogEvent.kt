package studio.papercube.library.simplelogger

import java.time.LocalDateTime

data class LogEvent(val time: LocalDateTime,
                    val level: LogLevel,
                    val content: String,
                    val tag: String? = null) {
    override fun toString(): String {
        return "#-- ${time.toLocalDate()} ${time.toLocalTime()} [$level][$tag] : $content"
    }
}