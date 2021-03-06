package studio.papercube.library.simplelogger

import org.junit.Test
import java.io.File
import java.io.StringWriter
import java.util.*
import java.util.concurrent.LinkedTransferQueue
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class LoggerTest {
    @Test
    fun testClass() {
        val logger = AsyncSimpleLogger(System.err.writer())

        for (i in 1..100000) {
            logger.v("null")
        }

        assert(true)
        return
    }

    @Test
    fun testMultiThread() {
        val tempDir = File(System.getProperty("java.io.tmpdir"))
        val tempFile = File(tempDir, "test.log.txt")
        val writer = StringWriter()
        val logger = AsyncSimpleLogger(writer)
//        val logger = AsyncSimpleLogger(tempFile.writer())
        (1..100).map { i ->
            Thread {
                val start = System.currentTimeMillis()
                val stop = 10000
                val random = Random()
                var written = 0
                while (System.currentTimeMillis() - start < stop) {
                    val bytes = ByteArray(10)
                    random.nextBytes(bytes)
                    val literal = bytes.contentToString()
                    val loggingHangup = measureNanoTime {
                        logger.v("$i : writing. $literal")
                    } / 1e6
                    logger.i("$i spent $loggingHangup milliseconds logging")
                    written++
                }
                val exitMsg = "$i wrote $written messages"
                logger.i(exitMsg)
                println(exitMsg)
            }.apply {
                name = "Writing to log $i"
                start()
            }
        }.forEach {
            it.join()
        }

        println("Awaiting logger to stop")
        val exitHangUp = measureTimeMillis {
            logger.stop()
        }
        println("Logger stopped in $exitHangUp milliseconds")

        println(tempFile.absolutePath)
    }

    @Test
    fun testLinkedQueue() {
        val queue = LinkedTransferQueue<TestObj>()
        for (i in 1..100) queue.offer(TestObj())
        var completedCycles = 0
        while (true) {
            for (i in 1..100) {
                queue.offer(TestObj())
            }
            for (i in 1..100) {
                queue.take()
            }

            completedCycles++
        }
    }

    class TestObj {
        companion object {
            var serialCounter: Long = 0
        }

        private val serial = serialCounter++

        override fun toString() = "TestObj #$serial"
    }
}