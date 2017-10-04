package studio.papercube.library.fileassist

import java.io.*
import java.util.*

open class DriveMarker private constructor(private val fileInDrive: File) {
    companion object {
        @JvmStatic private val randomHolder = Random()
        @JvmStatic fun resolve(fileInDrive: File) = DriveMarker(fileInDrive).apply { resolve() }
    }

    protected var idLength = 3

    private val idStorageFile: File = File("${fileInDrive.label}:/.volumeId")
    private var id: ByteArray? = ByteArray(0)

    init {

    }

    open fun resolve() {
        id = try {
            if (idStorageFile.exists()) readMultiByteID()
            else allocateNewID()
        } catch (e: Throwable) {
            try {
                allocateNewID()
            } catch (e: Throwable) {
                e.printStackTrace()
                null
            }
        }
    }

    open fun markID(): String {
        return id?.encodeHexString() ?: ByteArray(idLength).encodeHexString()
    }


    /**
     * @throws IOException
     */
    protected fun readMultiByteID(): ByteArray {
        DataInputStream(FileInputStream(idStorageFile)).use { input ->
            val bytes = ByteArray(idLength)
            for (i in 0..idLength - 1) {
                bytes[i] = input.readByte()
            }

            return bytes
        }
    }

    /**
     * @throws IOException
     */
    protected fun allocateNewID(): ByteArray {
        idStorageFile.createNewFile()
        DataOutputStream(FileOutputStream(idStorageFile)).use { output ->
            val bytes = ByteArray(idLength)
            randomHolder.nextBytes(bytes)

            output.write(bytes)
            return bytes
        }
    }

    protected fun ByteArray.encodeHexString(separator: String = ""):String {
        val hexValues = "0123456789ABCDEF"
        return this.map {
            hexValues[it.toInt() ushr 8 and 0xF].toString() +
                    hexValues[it.toInt() and 0xF].toString()
        }.joinToString(separator)
    }


}