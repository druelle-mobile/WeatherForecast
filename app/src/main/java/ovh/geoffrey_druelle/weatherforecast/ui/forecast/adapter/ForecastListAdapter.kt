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
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.databinding.ForecastListItemBinding
import ovh.geoffrey_druelle.weatherforecast.ui.forecast.ForecastListViewModel
import kotlin.coroutines.CoroutineContext

class ForecastListAdapter(
    var forecastList: List<ForecastEntity> = listOf(),
    private val forecastListViewModel: ForecastListViewModel
) : RecyclerView.Adapter<ForecastListAdapter.HomeViewHolder>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.forecast_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = forecastList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ForecastListItemBinding = DataBindingUtil.bind(view)!!
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        job.cancel()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}