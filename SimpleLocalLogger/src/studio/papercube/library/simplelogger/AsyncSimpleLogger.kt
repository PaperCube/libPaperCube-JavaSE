package studio.papercube.library.simplelogger

import java.io.PrintWriter
import java.io.Writer
import java.time.LocalDateTime

open class AsyncSimpleLogger(writer: Writer) : Logger() {
    private val printWriter = PrintWriter(AsyncWriter(writer), true)

    override fun log(logEvent: LogEvent) {
        val (time, level, content, tag) = logEvent
        printWriter.println("#-- ${time.toLocalDate()} ${time.toLocalTime()} [$level][$tag] : $content")
    }

    override fun v(tag: String?, msg: String) {
        log(createLogEvent(tag, msg, LogLevel.VERBOSE))
    }

    override fun i(tag: String?, msg: String) {
        log(createLogEvent(tag, msg, LogLevel.INFO))
    }

    override fun w(tag: String?, msg: String) {
        log(createLogEvent(tag, msg, LogLevel.WARNING))
    }

    override fun e(tag: String?, msg: String) {
        log(createLogEvent(tag, msg, LogLevel.ERROR))
    }

    private fun createLogEvent(tag: String?, msg: String, level: LogLevel): LogEvent {
        return LogEvent(LocalDateTime.now(), level, msg, tag)
    }

    override fun stop() {
        printWriter.close()
    }
}