package ovh.geoffrey_druelle.weatherforecast.ui.main.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.coroutines.runBlocking
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.appContext
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.instance
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CitiesListItemEntity
import ovh.geoffrey_druelle.weatherforecast.data.repository.CitiesListItemRepository

class SearchAdapter(
    context: Context,
    val layout: Int,
    private val item: List<CitiesListItemEntity>
) :
    ArrayAdapter<CitiesListItemEntity>(context, layout, item) {

    private val citiesListItemRepository = CitiesListItemRepository(instance)

    override fun getCount(): Int {
        return item.size
    }

    override fun getItem(position: Int): CitiesListItemEntity? {
        return item[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = convertView ?: layoutInflater.inflate(layout, null)
        val citiesListItem = getItem(position)
        val itemName = view.findViewById<TextView>(R.id.search_item)
        itemName.text = String.format(appContext.getString(R.string.city_country_s), citiesListItem?.name, citiesListItem?.country)

        return view
    }

    fun getCityId(position: Int): Long {
        val citiesListItem = getItem(position)

        return if (citiesListItem != null) runBlocking {
            citiesListItemRepository.getCityIdFromNameAndCountry(
                citiesListItem.name,
                citiesListItem.country
            )
        } else 2988507
    }

    fun getCity(position: Int): String {
        val citiesListItem = getItem(position)

        return if (citiesListItem != null) String.format(
            appContext.getString(R.string.city_country_s),
            citiesListItem.name,
            citiesListItem.country
        ) else context.getString(R.string.paris_fr)
    }
}