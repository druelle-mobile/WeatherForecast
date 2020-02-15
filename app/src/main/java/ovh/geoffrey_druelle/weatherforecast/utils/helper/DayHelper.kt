package ovh.geoffrey_druelle.weatherforecast.utils.helper

import android.graphics.Color
import ovh.geoffrey_druelle.weatherforecast.R
import java.text.SimpleDateFormat
import java.util.*

fun getDay(dt: Long): String {
    return when (getDayOfWeek(dt)) {
        Calendar.MONDAY -> "MONDAY"
        Calendar.TUESDAY -> "TUESDAY"
        Calendar.WEDNESDAY -> "WEDNESDAY"
        Calendar.THURSDAY -> "THURSDAY"
        Calendar.FRIDAY -> "FRIDAY"
        Calendar.SATURDAY -> "SATURDAY"
        Calendar.SUNDAY -> "SUNDAY"
        else -> "ERROR"
    }
}

fun getHour(dt_txt: String): String {
    return dt_txt.subSequence(dt_txt.length - 8, dt_txt.length - 3).toString()
}

fun attributeColorToDay(dt: Long): Int {
    return when (getDayOfWeek(dt)) {
        Calendar.MONDAY -> Color.parseColor("#B24C63")
        Calendar.TUESDAY -> Color.parseColor("#357DED")
        Calendar.WEDNESDAY -> Color.parseColor("#56EEF4")
        Calendar.THURSDAY -> Color.parseColor("#4A1942")
        Calendar.FRIDAY -> Color.parseColor("#119DA4")
        Calendar.SATURDAY -> Color.parseColor("#3066BE")
        Calendar.SUNDAY -> Color.parseColor("#B388EB")
        else -> Color.GRAY
    }
}

private fun getDayOfWeek(dt: Long): Int {
    val date = Date(dt * 1000)

    val calendar = Calendar.getInstance()
    calendar.time = date

    return calendar.get(Calendar.DAY_OF_WEEK)
}

fun getDayAndHours(time: Long): String {
    val date = Date(time * 1000)
    val simpleDateFormatError =  SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    return simpleDateFormatError.format(date)
}
