package studio.papercube.library.fileassist

import org.junit.Test

import org.junit.Assert.*
import studio.papercube.library.fileassist.DriveMarker.Companion.encodeHexString

class DriveMarkerTest {

    @Test
    fun encodeHexString() {
        val testMap = mapOf(
                byteArrayOf(0x43, 0x71) to "4371"
        )

        for ((byteArray, str) in testMap) {
            assertEquals(
                    byteArray.encodeHexString().toUpperCase(),
                    str.toUpperCase())
        }
    }
}