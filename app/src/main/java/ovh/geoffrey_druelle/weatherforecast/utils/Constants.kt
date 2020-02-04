package ovh.geoffrey_druelle.weatherforecast.utils

// Database constants
const val DB_NAME = "Weather_Forecast_Database"

// Network constants
// Different timeouts for the OkHttpClient
const val CONNECT_TIMEOUT = 15L
const val WRITE_TIMEOUT = 15L
const val READ_TIMEOUT = 15L
// Base of the url of used API
const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
const val ICON_URL = "http://openweathermap.org/img/wn/"