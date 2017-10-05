package studio.papercube.library.simplelogger

import org.junit.Assert.*
import org.junit.Test

class LoggerTest{
    val logger = AsyncSimpleLogger(System.err.writer())

    @Test
    fun testClass() {
        for(i in 1..100000){
            logger.v("null")
        }

        assert(true)
        return
    }
}