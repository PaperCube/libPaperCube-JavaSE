package studio.papercube.library.simplelogger

import java.io.IOException
import java.io.Writer
import java.util.*
import java.util.concurrent.LinkedTransferQueue

open class AsyncWriter(private val writer: Writer,
                       threadName: String,
                       autoStart: Boolean = true) : Writer() {
    constructor(writer: Writer, autoStart: Boolean = true) : this(writer, "AsyncWriterWorker", autoStart)

    private var isClosed = false
    private val taskQueue = LinkedTransferQueue<() -> Any?>()
    private val thread: Thread = Thread {
        val currentThread = Thread.currentThread()
        while (!currentThread.isInterrupted) {
            try {
                taskQueue.take()()
            } catch (e: InterruptedException) {
                break
            }
        }
    }

    var threadName: String
        set(value) {
            thread.name = value
        }
        get() = thread.name

    init {
        this.threadName = threadName
        if (autoStart) {
            thread.start()
        }
    }

    override fun write(cbuf: CharArray?, off: Int, len: Int) {
        val buffered = Arrays.copyOfRange(cbuf, off, off + len)
        synchronized(lock) {
            ensureOpen()
            taskQueue.offer {
                writer.write(buffered, 0, len)
            }
        }
    }

    override fun flush() {
        synchronized(lock) {
            ensureOpen()
            taskQueue.offer {
                writer.flush()
            }
        }
    }

    override fun close() {
        synchronized(lock) {
            if (isClosed) return@synchronized
            try {
                thread.interrupt()
                thread.join()
                for (element: (() -> Any?)? in taskQueue) {
                    element?.invoke()
                }
            } catch (e: InterruptedException) {
            } finally {
                try {
                    writer.close()
                } catch (e: IOException) {
                    throw e
                }
            }
        }
    }

    protected fun ensureOpen() {
        if (isClosed)
            throw IOException("Stream closed")
    }

    protected fun startThread() {
        thread.start()
    }
}