package studio.papercube.library.simplelogger

import org.junit.Test
import java.time.LocalTime
import java.util.*

class TimeFormatterTest {

    @Test
    fun formatTime() {
        val timeMap = mapOf(
                createLocalTime(2, 31, 59, 58) to "02:31:59.058",
                createLocalTime(17, 45, 10, 382) to "17:45:10.382",
                createLocalTime(18, 54, 46, 519) to "18:54:46.519",
                createLocalTime(0, 9, 49, 946) to "00:09:49.946",
                createLocalTime(13, 27, 50, 871) to "13:27:50.871",
                createLocalTime(10, 54, 10, 217) to "10:54:10.217",
                createLocalTime(0, 47, 7, 977) to "00:47:07.977",
                createLocalTime(14, 9, 8, 339) to "14:09:08.339",
                createLocalTime(15, 9, 25, 776) to "15:09:25.776",
                createLocalTime(7, 11, 24, 258) to "07:11:24.258",
                createLocalTime(18, 26, 14, 902) to "18:26:14.902",
                createLocalTime(17, 33, 22, 924) to "17:33:22.924",
                createLocalTime(10, 10, 55, 34) to "10:10:55.034",
                createLocalTime(18, 39, 36, 214) to "18:39:36.214",
                createLocalTime(18, 1, 40, 194) to "18:01:40.194",
                createLocalTime(3, 44, 7, 498) to "03:44:07.498",
                createLocalTime(0, 46, 18, 473) to "00:46:18.473",
                createLocalTime(15, 15, 47, 993) to "15:15:47.993",
                createLocalTime(11, 18, 37, 562) to "11:18:37.562",
                createLocalTime(1, 31, 8, 706) to "01:31:08.706",
                createLocalTime(12, 57, 36, 650) to "12:57:36.650",
                createLocalTime(23, 56, 37, 41) to "23:56:37.041",
                createLocalTime(7, 43, 58, 417) to "07:43:58.417",
                createLocalTime(19, 27, 46, 351) to "19:27:46.351",
                createLocalTime(8, 12, 41, 873) to "08:12:41.873",
                createLocalTime(21, 22, 15, 872) to "21:22:15.872",
                createLocalTime(16, 56, 40, 709) to "16:56:40.709",
                createLocalTime(12, 41, 36, 56) to "12:41:36.056",
                createLocalTime(8, 43, 33, 963) to "08:43:33.963",
                createLocalTime(20, 41, 32, 773) to "20:41:32.773",
                createLocalTime(2, 6, 25, 930) to "02:06:25.930",
                createLocalTime(7, 37, 56, 768) to "07:37:56.768",
                createLocalTime(5, 8, 29, 484) to "05:08:29.484",
                createLocalTime(22, 35, 51, 419) to "22:35:51.419",
                createLocalTime(15, 13, 29, 245) to "15:13:29.245",
                createLocalTime(7, 18, 1, 667) to "07:18:01.667",
                createLocalTime(18, 18, 7, 513) to "18:18:07.513",
                createLocalTime(22, 15, 10, 735) to "22:15:10.735",
                createLocalTime(1, 26, 2, 265) to "01:26:02.265",
                createLocalTime(5, 47, 30, 150) to "05:47:30.150",
                createLocalTime(9, 38, 24, 206) to "09:38:24.206",
                createLocalTime(14, 26, 16, 701) to "14:26:16.701",
                createLocalTime(19, 1, 10, 596) to "19:01:10.596",
                createLocalTime(21, 35, 37, 958) to "21:35:37.958",
                createLocalTime(22, 12, 44, 464) to "22:12:44.464",
                createLocalTime(0, 41, 31, 634) to "00:41:31.634",
                createLocalTime(15, 55, 28, 90) to "15:55:28.090",
                createLocalTime(8, 39, 59, 200) to "08:39:59.200",
                createLocalTime(10, 49, 5, 723) to "10:49:05.723",
                createLocalTime(8, 24, 21, 330) to "08:24:21.330",
                createLocalTime(7, 28, 58, 576) to "07:28:58.576",
                createLocalTime(10, 47, 49, 790) to "10:47:49.790",
                createLocalTime(8, 30, 42, 812) to "08:30:42.812",
                createLocalTime(15, 15, 14, 403) to "15:15:14.403",
                createLocalTime(15, 36, 59, 305) to "15:36:59.305",
                createLocalTime(13, 3, 25, 495) to "13:03:25.495",
                createLocalTime(18, 3, 17, 152) to "18:03:17.152",
                createLocalTime(8, 20, 16, 23) to "08:20:16.023",
                createLocalTime(9, 33, 17, 819) to "09:33:17.819",
                createLocalTime(19, 50, 0, 783) to "19:50:00.783",
                createLocalTime(4, 0, 8, 986) to "04:00:08.986",
                createLocalTime(6, 10, 12, 117) to "06:10:12.117",
                createLocalTime(22, 31, 19, 410) to "22:31:19.410",
                createLocalTime(7, 6, 46, 786) to "07:06:46.786",
                createLocalTime(22, 26, 26, 231) to "22:26:26.231",
                createLocalTime(18, 42, 31, 152) to "18:42:31.152",
                createLocalTime(11, 57, 19, 947) to "11:57:19.947",
                createLocalTime(4, 48, 16, 788) to "04:48:16.788",
                createLocalTime(13, 21, 2, 972) to "13:21:02.972",
                createLocalTime(1, 28, 33, 693) to "01:28:33.693",
                createLocalTime(17, 50, 20, 545) to "17:50:20.545",
                createLocalTime(19, 41, 30, 915) to "19:41:30.915",
                createLocalTime(11, 19, 26, 253) to "11:19:26.253",
                createLocalTime(19, 26, 10, 688) to "19:26:10.688",
                createLocalTime(10, 32, 16, 714) to "10:32:16.714",
                createLocalTime(7, 34, 26, 154) to "07:34:26.154",
                createLocalTime(9, 34, 4, 23) to "09:34:04.023",
                createLocalTime(21, 35, 40, 534) to "21:35:40.534",
                createLocalTime(10, 38, 22, 874) to "10:38:22.874",
                createLocalTime(12, 38, 18, 638) to "12:38:18.638",
                createLocalTime(19, 53, 48, 702) to "19:53:48.702",
                createLocalTime(7, 17, 54, 660) to "07:17:54.660",
                createLocalTime(2, 46, 16, 70) to "02:46:16.070",
                createLocalTime(2, 22, 33, 124) to "02:22:33.124",
                createLocalTime(2, 20, 11, 463) to "02:20:11.463",
                createLocalTime(5, 19, 38, 253) to "05:19:38.253",
                createLocalTime(14, 14, 50, 698) to "14:14:50.698",
                createLocalTime(14, 40, 22, 192) to "14:40:22.192",
                createLocalTime(3, 54, 48, 222) to "03:54:48.222",
                createLocalTime(17, 26, 7, 295) to "17:26:07.295",
                createLocalTime(15, 43, 52, 554) to "15:43:52.554",
                createLocalTime(9, 23, 26, 159) to "09:23:26.159",
                createLocalTime(6, 8, 17, 849) to "06:08:17.849",
                createLocalTime(21, 18, 39, 694) to "21:18:39.694",
                createLocalTime(9, 4, 58, 131) to "09:04:58.131",
                createLocalTime(6, 47, 57, 284) to "06:47:57.284",
                createLocalTime(14, 12, 10, 298) to "14:12:10.298",
                createLocalTime(21, 21, 17, 974) to "21:21:17.974",
                createLocalTime(21, 58, 35, 473) to "21:58:35.473",
                createLocalTime(15, 38, 29, 982) to "15:38:29.982"
        )

        for ((localTime, formatted) in timeMap) {
            assert(TimeFormatter.formatTime(localTime) == formatted)
        }
    }

    private fun generateTestCode() {
        val r = Random()

        for (i in 1..100) {
            val h = r.nextInt(24)
            val m = r.nextInt(60)
            val s = r.nextInt(60)
            val ms = r.nextInt(1000)
            println("createLocalTime(%d,%d,%d,%d) to \"%02d:%02d:%02d.%03d\",".format(h, m, s, ms, h, m, s, ms))
        }

        return
    }

    private fun createLocalTime(hour: Int, minute: Int, second: Int, millisecond: Int): LocalTime {
        return LocalTime.of(hour, minute, second, millisecond * 1_000_000)
    }
}