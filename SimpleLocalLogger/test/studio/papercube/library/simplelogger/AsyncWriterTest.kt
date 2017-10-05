package studio.papercube.library.simplelogger

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

class AsyncWriterTest {
    @Test
    fun test() {
        val stringWriter = StringWriter()
        val writer = PrintWriter(AsyncWriter(stringWriter), true)
        val random = Random()
        for (i in 1..1000) {
            Thread.sleep(random.nextInt(15).toLong())
            writer.write(i.toString())
        }
        writer.close()
        assertEquals(
                (1..1000).joinToString(separator = "") { it.toString() },
                stringWriter.toString()
        )
    }

    private class StopWatch {
        val start = System.currentTimeMillis()
        val relative get() = System.currentTimeMillis() - start
    }
}