package ovh.geoffrey_druelle.weatherforecast.utils.helper

import android.graphics.Color
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

fun attributeColorToDay(dt: Long): Int {
    return when (getDayOfWeek(dt)) {
        Calendar.MONDAY -> Color.parseColor("#28E0AE")
        Calendar.TUESDAY -> Color.parseColor("#FF0090")
        Calendar.WEDNESDAY -> Color.parseColor("#FFAE00")
        Calendar.THURSDAY -> Color.parseColor("#0090FF")
        Calendar.FRIDAY -> Color.parseColor("#DC0000")
        Calendar.SATURDAY -> Color.parseColor("#0051FF")
        Calendar.SUNDAY -> Color.parseColor("#3D28E0")
        else -> Color.parseColor("#28E0AE")
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
