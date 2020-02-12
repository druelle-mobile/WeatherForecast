package ovh.geoffrey_druelle.weatherforecast.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ovh.geoffrey_druelle.weatherforecast.core.BaseViewModel
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CitiesListItemEntity

class MainActivityViewModel : BaseViewModel() {

    val appBarTitle: ObservableField<String> = ObservableField()

}