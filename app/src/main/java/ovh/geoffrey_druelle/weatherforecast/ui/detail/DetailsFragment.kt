package ovh.geoffrey_druelle.weatherforecast.ui.detail

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.details_fragment.view.*
import kotlinx.coroutines.runBlocking
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.data.repository.CityRepository
import ovh.geoffrey_druelle.weatherforecast.data.repository.ForecastRepository
import ovh.geoffrey_druelle.weatherforecast.utils.helper.getDay
import ovh.geoffrey_druelle.weatherforecast.utils.helper.getDayAndHours
import ovh.geoffrey_druelle.weatherforecast.utils.helper.getIcon
import ovh.geoffrey_druelle.weatherforecast.utils.helper.getMainDescription

class DetailsFragment : DialogFragment() {


    private lateinit var forecast: ForecastEntity
    private lateinit var customView: View
    private var forecastRepository = ForecastRepository(WeatherForecastApplication.instance)
    private var cityRepository = CityRepository(WeatherForecastApplication.instance)

    companion object {

        private var SELECTED_FORECAST = "SELECTED_FORECAST"
        private const val TAG = "DetailsFragment"

        private fun newInstance(forecast: ForecastEntity): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(SELECTED_FORECAST, forecast)
            detailsFragment.arguments = arguments
            return detailsFragment
        }

        fun show(
            fragmentTransaction: FragmentTransaction,
            forecast: ForecastEntity
        ): DetailsFragment {
            val dialog = newInstance(forecast)
            dialog.show(fragmentTransaction, TAG)
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecast = arguments?.getParcelable(SELECTED_FORECAST)!!
        isCancelable = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity!!.layoutInflater.inflate(R.layout.details_fragment, null)
        customView = view

//        Picasso.get().load(forecast.cover).resize(400,500).onlyScaleDown().into(view.imageview_cover)
//        view.textview_price.text = String.format(resources.getString(R.string.price_in_euros), book.price)
//        view.textview_summary.text = TextUtils.join("\n\n", forecast.synopsis)
        view.weather_icon.setImageResource(getIcon(forecast.weatherId, forecast.weatherIcon))
        view.weather_desc.text = getMainDescription(forecast.weatherId)
        view.temperature.text = String.format("%.2f °C", forecast.temp)
        view.feel_temperature.text = String.format("Felt : %.2f °C", forecast.feelsLike)
        view.max_temmperature.text = String.format("Max : %.2f °C", forecast.tempMax)
        view.min_temmperature.text = String.format("Min : %.2f °C", forecast.tempMin)
        if (forecast.temp >= 15.0) view.hot_or_cold.text = "Hot"
        else view.hot_or_cold.text = "Cold"
        view.wind_speed.text = String.format("%.2f km/h", forecast.speedWind)
        view.rain_three_hours.text =
            String.format("Rain : %.2f mm", forecast.volumeRainLastThreeHours)
        view.snow_three_hours.text =
            String.format("Rain : %.2f mm", forecast.volumeSnowLastThreeHours)

        val city: CityEntity = runBlocking { cityRepository.getCity(forecast.cityId) }
        view.sunrise.text = String.format("Sunrise : %s", getDayAndHours(city.sunrise))
        view.sunset.text = String.format("Sunset : %s", getDayAndHours(city.sunset))


        val builder = AlertDialog.Builder(context!!)
            .setTitle(setTitle())
            .setView(view)
            .setNegativeButton(getString(R.string.quit)) { _: DialogInterface, _: Int ->
                dialog?.cancel()
            }

        return builder.create()
    }

    private fun setTitle(): String {
        val dayOfWeak = getDay(forecast.dt)
        val city = runBlocking { cityRepository.getCity(forecast.cityId) }
        val cityName = city.name
        val cityCountry = city.country
        return String.format("%s - %s, %s", dayOfWeak, cityName, cityCountry)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
