package studio.papercube.library.simplelogger

import java.io.Writer
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

open class MulticastWriter() : Writer() {
    private val writers: MutableList<Writer> = ArrayList()
    private val writersListLock = ReentrantLock()

    constructor(writers: List<Writer>) : this() {
        this.writers.addAll(writers)
    }

    override fun write(cbuf: CharArray?, off: Int, len: Int) {
        withCurrentlyHeldWriters { it.write(cbuf, off, len) }
    }

    override fun flush() {
        withCurrentlyHeldWriters { it.flush() }
    }


    override fun close() {
        withCurrentlyHeldWriters { it.close() }
    }

    open fun println(any: Any?) {
        println(any.toString())
    }

    open fun println(str: String) {
        write(str, 0, str.length)
    }

    open fun addWriter(writer: Writer) {
        writersListLock.withLock {
            writers.add(writer)
        }
    }

    open fun removeWriter(writer: Writer): Boolean {
        writersListLock.withLock {
            return writers.remove(writer)
        }
    }

    private inline fun withCurrentlyHeldWriters(op: (Writer) -> Unit) {
        writersListLock.lock()
        val capture = writers.toTypedArray()
        writersListLock.unlock()
        for (writer in capture) {
            synchronized(writer) {
                op(writer)
            }
        }
    }
}