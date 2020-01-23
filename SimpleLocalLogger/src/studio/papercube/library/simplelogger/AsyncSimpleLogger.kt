package studio.papercube.library.simplelogger

import studio.papercube.library.simplelogger.TimeFormatter.currentTimeDividedWithHyphens
import java.io.File
import java.io.PrintWriter
import java.io.Writer
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

open class AsyncSimpleLogger(writer: Writer) : Logger() {
    private val printWriter = PrintWriter(AsyncWriter(writer), true)
    protected val lock = ReentrantLock(true)

    companion object {
        fun inDirectory(logDir: File): AsyncSimpleLogger {
            val dateTodayString = LocalDate.now().toString()
            logDir.mkdirs()
            val file = File(logDir, "Log_${dateTodayString}_$currentTimeDividedWithHyphens.txt")
            file.createNewFile()
            return AsyncSimpleLogger(MulticastWriter(file.writer(), System.out.writer()))
        }
    }

    override fun log(logEvent: LogEvent) {
        lock.withLock {
            if (!enabled) return
            val (time, level, content, tag) = logEvent
            printWriter.println("#-- ${time.toLocalDate()} ${TimeFormatter.formatTime(time)} [$level][$tag] : $content")
        }
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