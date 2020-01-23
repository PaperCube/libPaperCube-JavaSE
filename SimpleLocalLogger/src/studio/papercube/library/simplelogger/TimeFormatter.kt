package studio.papercube.library.simplelogger

import java.time.LocalDateTime
import java.time.LocalTime

object TimeFormatter{
    /**
     * Return a string of [localTime] in HH:mm:ss.SSS
     */
    fun formatTime(localTime:LocalTime):String{
        with(localTime){
            return "%02d:%02d:%02d.%03d".format(hour, minute, second, nano / 1_000_000)
        }
    }

    /**
     * Return a string of the [LocalTime] instance generated by [localDateTime.toLocalDate()][LocalDateTime.toLocalDate]
     * in HH:mm:ss.SSS
     */
    fun formatTime(localDateTime: LocalDateTime): String {
        return formatTime(localDateTime.toLocalTime())
    }

    val currentTimeDividedWithHyphens:String get(){
        val time = LocalTime.now()
        with(time){
            return "%02d-%02d-%02d.%03d".format(hour, minute, second, nano / 1_000_000)
        }
    }
}