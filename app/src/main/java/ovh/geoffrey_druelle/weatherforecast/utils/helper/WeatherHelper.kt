package ovh.geoffrey_druelle.weatherforecast.utils.helper

import ovh.geoffrey_druelle.weatherforecast.R

// This class is here to help by getting the right icon
// depending on id and ico of the "weather" JSON object
// Let's have a look here : https://openweathermap.org/weather-conditions

//open class WeatherHelper {


fun getIcon(id: Int, icon: String): Int {
    var isDay = false
    if (icon.contains("d")) isDay == true

    return when (id) {
        in 200..232 -> {
            if (isDay) R.drawable.thunderstorm_day
            else R.drawable.thunderstorm_night
        }
        in 300..321 -> {
            if (isDay) R.drawable.shower_rain_day
            else R.drawable.shower_rain_night
        }
        in 500..504 -> {
            if (isDay) R.drawable.rain_day
            else R.drawable.rain_night
        }
        511 -> {
            if (isDay) R.drawable.snow_day
            else R.drawable.snow_night
        }
        in 520..531 -> {
            if (isDay) R.drawable.shower_rain_day
            else R.drawable.shower_rain_night
        }
        in 600..622 -> {
            if (isDay) R.drawable.snow_day
            else R.drawable.snow_night
        }
        in 701..781 -> {
            if (isDay) R.drawable.mist_day
            else R.drawable.mist_night
        }
        800 -> {
            if (isDay) R.drawable.clear_sky_day
            else R.drawable.clear_sky_night
        }
        801 -> {
            if (isDay) R.drawable.few_clouds_day
            else R.drawable.few_clouds_night
        }
        802 -> {
            if (isDay) R.drawable.scattered_clouds_day
            else R.drawable.scattered_clouds_night
        }
        in 803..804 -> {
            if (isDay) R.drawable.thunderstorm_day
            else R.drawable.thunderstorm_night
        }
        else -> R.drawable.ic_clear_red_24dp
    }
}

fun getMainDescription(id: Int): String {
    return when (id) {
        in 200..232 -> {
            "Thunderstorm"
        }
        in 300..321 -> {
            "Drizzle"
        }
        in 500..531 -> {
            "Rain"
        }
        in 600..622 -> {
            "Snow"
        }
        701 -> "Mist"
        711 -> "Smoke"
        721 -> "Haze"
        731, 761 -> "Dust"
        741 -> "Fog"
        751 -> "Sand"
        762 -> "Ash"
        771 -> "Squall"
        781 -> "Tornado"
        800 -> "Clear"
        in 801..804 -> "Clouds"
        else -> "NC"
    }
}
//}