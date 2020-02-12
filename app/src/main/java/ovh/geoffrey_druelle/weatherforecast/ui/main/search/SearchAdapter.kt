package ovh.geoffrey_druelle.weatherforecast.ui.main.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CitiesListItemEntity

class SearchAdapter(context: Context, val layout: Int, private val item: List<CitiesListItemEntity>) :
    ArrayAdapter<CitiesListItemEntity>(context, layout, item) {

    override fun getCount(): Int {
        return item.size
    }

    override fun getItem(position: Int): CitiesListItemEntity? {
        return item[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = convertView ?: layoutInflater.inflate(layout, null)
        val citiesListItem = getItem(position)
        val itemName = view.findViewById<TextView>(R.id.search_item)
        itemName.text = String.format("%s, %s", citiesListItem?.name, citiesListItem?.country)

        return view
    }


}