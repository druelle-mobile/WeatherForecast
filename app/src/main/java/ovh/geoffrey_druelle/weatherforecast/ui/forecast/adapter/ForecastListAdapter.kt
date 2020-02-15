package ovh.geoffrey_druelle.weatherforecast.ui.forecast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.appContext
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.databinding.ForecastListItemBinding
import ovh.geoffrey_druelle.weatherforecast.ui.detail.DetailsFragment
import ovh.geoffrey_druelle.weatherforecast.ui.forecast.ForecastListViewModel
import ovh.geoffrey_druelle.weatherforecast.ui.main.MainActivity
import ovh.geoffrey_druelle.weatherforecast.utils.helper.*
import kotlin.coroutines.CoroutineContext


class ForecastListAdapter(
    var forecastList: MutableList<ForecastEntity> = mutableListOf(),
    private val forecastListViewModel: ForecastListViewModel
) : RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.forecast_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = forecastList.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.binding.forecastListItem = forecast
        holder.binding.vm = forecastListViewModel

        holder.binding.weatherIcon.setImageResource(getIcon(forecast.weatherId, forecast.weatherIcon))
        holder.binding.weatherDesc.text = getMainDescription(forecast.weatherId)

        holder.binding.day.text = getDay(forecast.dt)
        holder.binding.hour.text = getHour(forecast.dt_txt)
        holder.binding.forecastCard.setBackgroundColor(attributeColorToDay(forecast.dt))
        holder.binding.temperature.text = String.format(appContext.getString(R.string.temp_s), forecast.temp)

        holder.binding.forecastCard.setOnClickListener {
            openDetails(forecast)
        }
    }

    private fun openDetails(forecast: ForecastEntity) {
        val fragmentTransaction = MainActivity.instance.supportFragmentManager.beginTransaction()
        DetailsFragment.show(fragmentTransaction, forecast)
    }

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ForecastListItemBinding = DataBindingUtil.bind(view)!!
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        job.cancel()
        super.onDetachedFromRecyclerView(recyclerView)
    }

}