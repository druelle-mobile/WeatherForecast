package ovh.geoffrey_druelle.weatherforecast.utils.helper

import android.widget.ImageView
import ovh.geoffrey_druelle.weatherforecast.utils.ICON_URL
import ovh.geoffrey_druelle.weatherforecast.utils.extension.paint

// This class is here to help by getting the right icon
// depending on id and ico of the "weather" JSON object
// Let's have a look here : https://openweathermap.org/weather-conditions

class WeatherIconHelper {
    var isDay: Boolean = false

    fun getDrawable(id: Int, icon: String, imageView: ImageView) {
        if (icon.contains("d")) isDay

        when (id) {
            in 200..232 -> {
//                if (isDay) paint(ICON_URL+"11d@2x.png", imageView)
                if (isDay) paint(ICON_URL +"11d@2x.png", imageView)
//                else paint(ICON_URL+"11n@2x.png", imageView)
                else paint(ICON_URL +"11n@2x.png", imageView)
            }
            in 300..321 -> {
                if (isDay) paint(ICON_URL +"09d@2x.png", imageView)
                else paint(ICON_URL +"09n@2x.png", imageView)
            }
            in 500..504 -> {
                if (isDay) paint(ICON_URL +"10d@2x.png", imageView)
                else paint(ICON_URL +"10n@2x.png", imageView)
            }
            511 -> {
                if (isDay) paint(ICON_URL +"13d@2x.png", imageView)
                else paint(ICON_URL +"13n@2x.png", imageView)
            }
            in 520..531 -> {
                if (isDay) paint(ICON_URL +"09d@2x.png", imageView)
                else paint(ICON_URL +"09n@2x.png", imageView)
            }
            in 600..622 -> {
                if (isDay) paint(ICON_URL +"13d@2x.png", imageView)
                else paint(ICON_URL +"13n@2x.png", imageView)
            }
            in 701..781 -> {
                if (isDay) paint(ICON_URL +"50d@2x.png", imageView)
                else paint(ICON_URL +"50n@2x.png", imageView)
            }
            800 -> {
                if (isDay) paint(ICON_URL +"01d@2x.png", imageView)
                else paint(ICON_URL +"01n@2x.png", imageView)
            }
            801 -> {
                if (isDay) paint(ICON_URL +"02d@2x.png", imageView)
                else paint(ICON_URL +"02n@2x.png", imageView)
            }
            802 -> {
                if (isDay) paint(ICON_URL +"03d@2x.png", imageView)
                else paint(ICON_URL +"03n@2x.png", imageView)
            }
            in 803..804 -> {
                if (isDay) paint(ICON_URL +"11d@2x.png", imageView)
                else paint(ICON_URL +"11n@2x.png", imageView)
            }
        }
    }
}