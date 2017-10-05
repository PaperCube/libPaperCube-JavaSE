package studio.papercube.library.simplelogger

abstract class Logger {
    abstract fun v(tag: String?, msg: String)
    abstract fun i(tag: String?, msg: String)
    abstract fun w(tag: String?, msg: String)
    abstract fun e(tag: String?, msg: String)

    open fun v(msg: String) {
        v(null, msg)
    }

    open fun i(msg: String) {
        i(null, msg)
    }

    open fun w(msg: String) {
        w(null, msg)
    }

    open fun e(msg: String) {
        e(null, msg)
    }

    abstract fun log(logEvent: LogEvent)
    open fun stop() {}
}