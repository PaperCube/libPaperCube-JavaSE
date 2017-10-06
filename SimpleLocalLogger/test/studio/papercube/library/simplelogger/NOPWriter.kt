package studio.papercube.library.simplelogger

import java.io.Writer

class NOPWriter : Writer() {
    override fun write(cbuf: CharArray?, off: Int, len: Int) {
        nop()
    }

    override fun flush() {
        nop()
    }

    override fun close() {
        nop()
    }

    private fun nop() {}
}