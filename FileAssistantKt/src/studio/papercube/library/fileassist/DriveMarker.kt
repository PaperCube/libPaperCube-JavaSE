package studio.papercube.library.fileassist

import java.io.*
import java.util.*

open class DriveMarker protected constructor(private val idStorageFile: File) {
    companion object {
        @JvmStatic private val randomHolder = Random()
        @JvmStatic
        fun resolve(fileInDrive: File) = DriveMarker.inDrive(fileInDrive).apply { resolve() }

        @JvmStatic
        fun inDirectory(dir: File): DriveMarker {
            return DriveMarker(File(dir, ".volumeId"))
        }

        @JvmStatic
        fun inDrive(file: File): DriveMarker {
            return inDirectory(File("${file.label}:\\"))
        }

        @JvmStatic
        fun ByteArray.encodeHexString(separator: String = ""): String {
            val hexValues = "0123456789ABCDEF"
            return this.map {
                hexValues[it.toInt() ushr 4 and 0xF].toString() +
                        hexValues[it.toInt() and 0xF].toString()
            }.joinToString(separator)
        }
    }

    protected var idLength = 3

    private var id: ByteArray? = null

    init {

    }

    /**
     * @throws IOException if there's a problem with IO
     */
    open fun resolve(): ByteArray {
        val newId:ByteArray = try {
            if (idStorageFile.exists()) readMultiByteID()
            else allocateNewID()
        } catch (e: Exception) {
            try {
                allocateNewID()
            } catch (eAlloc: Exception) {
                eAlloc.addSuppressed(e)
                throw eAlloc
            }
        }
        id = newId
        return newId
    }

    open fun tryResolve(): ByteArray? {
        return try {
            resolve()
        } catch (e: Exception) {
            null
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
            for (i in 0 until idLength) {
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




}