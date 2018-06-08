package studio.papercube.library.simplelogger

import java.io.PrintWriter
import java.io.StringWriter

abstract class Logger {
    open var enabled = true

    abstract fun v(tag: String?, msg: String)
    abstract fun i(tag: String?, msg: String)
    abstract fun w(tag: String?, msg: String)
    abstract fun e(tag: String?, msg: String)

    open fun v(msg: String) {
        v(getCallerName(), msg)
    }

    open fun i(msg: String) {
        i(getCallerName(), msg)
    }

    open fun w(msg: String) {
        w(getCallerName(), msg)
    }

    open fun e(msg: String) {
        e(getCallerName(), msg)
    }

    open fun e(e: Throwable) {
        e(getCallerName(), e.detailedString)
    }

    open fun e(tag: String? = null, msg: String? = null, e: Throwable) {
        if (msg == null) {
            e(tag, e.detailedString)
        } else {
            e(tag, msg)
            e(tag, e.detailedString)
        }
    }


    private val Throwable.detailedString: String
        get() {
            val stringWriter = StringWriter()
            val printWriter = PrintWriter(stringWriter)
            this.printStackTrace(printWriter)
            printWriter.close()
            return stringWriter.toString()
        }

    private fun getCallerName(): String {
        val callerStacktrace = Thread.currentThread().stackTrace[3]
        val callerClass = callerStacktrace.className.substringAfterLast('.')
        val callerMethodName = callerStacktrace.methodName
        return "$callerClass.$callerMethodName"
    }

    abstract fun log(logEvent: LogEvent)
    open fun stop() {}
}