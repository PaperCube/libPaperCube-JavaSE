package studio.papercube.library.fileassist

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.*

enum class Platform {
    WINDOWS,
    MAC,
    LINUX,
    GENERAL_UNIX,
    GENERAL,
    UNCERTAIN;
}


@Retention(SOURCE)
@Target(FUNCTION, CLASS, PROPERTY)
annotation class PlatformSpecific(val platform: Platform)

@Retention(SOURCE)
@Target(FUNCTION, CLASS, PROPERTY)
annotation class CrossPlatform


