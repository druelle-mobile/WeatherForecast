package ovh.geoffrey_druelle.weatherforecast.ui.main

import androidx.databinding.ObservableField
import ovh.geoffrey_druelle.weatherforecast.core.BaseViewModel

class MainViewModel : BaseViewModel() {
    var appBarTitle: ObservableField<String> = ObservableField()
}