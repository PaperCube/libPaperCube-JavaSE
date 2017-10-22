package studio.papercube.library.fileassist

import java.io.File
import java.util.regex.Pattern
import javax.swing.filechooser.FileSystemView

private val illegalFileNameRegex = Pattern.compile("[\\\\|?:<>\"/*]").toRegex()

fun File.getVolumeLabel(): String {
    return FileSystemView.getFileSystemView().getSystemDisplayName(toRootDirectory()).takeUnless { it.isBlank() } ?: "Unknown Drive Label"
}

@PlatformSpecific(Platform.WINDOWS)
fun File.toRootDirectory(): File {
    return File("$label:/")
}

fun String.validateFileName(): String {
    return this.replace(illegalFileNameRegex, "")
}

val File.label get() = absolutePath[0]
