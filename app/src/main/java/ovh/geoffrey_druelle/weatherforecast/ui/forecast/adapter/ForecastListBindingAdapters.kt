package ovh.geoffrey_druelle.weatherforecast.ui.forecast.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.ui.forecast.ForecastListViewModel

@BindingAdapter(value = ["forecastList","forecastListViewModel"], requireAll = false)
fun setRecyclerViewSource(
    recyclerView: RecyclerView,
    list: List<ForecastEntity>,
    viewModel: ForecastListViewModel
) {
    recyclerView.adapter?.run {
        if (this is ForecastListAdapter) {
            this.forecastList = list
            this.notifyDataSetChanged()
        }
    } ?: run {
        ForecastListAdapter(list, viewModel).apply {
            recyclerView.adapter = this
        }
    }
}