package ovh.geoffrey_druelle.weatherforecast.ui.generic2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.instance
import ovh.geoffrey_druelle.weatherforecast.core.BaseViewModel
import ovh.geoffrey_druelle.weatherforecast.data.local.model.GenericModel
import ovh.geoffrey_druelle.weatherforecast.data.repository.GenericRepository
import ovh.geoffrey_druelle.weatherforecast.utils.extension.with
import timber.log.Timber.e
import kotlin.coroutines.CoroutineContext

class Generic2ViewModel : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    private var repo = GenericRepository(instance)
    private lateinit var subscription: Disposable

    private val _isGeneric = MutableLiveData<Boolean>()
    val isGeneric: LiveData<Boolean>
        get() = _isGeneric

    private val _genericText = MutableLiveData<String>()
    val genericText: LiveData<String>
        get() = _genericText

    private val _genericNumber = MutableLiveData<String>()
    val genericNumber: LiveData<String>
        get() = _genericNumber

    // Beware : totally arbitrary for example
    private val _genericList = MutableLiveData<List<GenericModel>>()
    val genericList: LiveData<List<GenericModel>>
        get() = _genericList

    var appVersion: String = instance.getVersionName()

    init {
        _isGeneric.postValue(true)
        loadGenericList()
    }

    private fun loadGenericList() {
        subscription = repo.getGenericList()
            .with()
            .subscribe({
                _genericList.value = it
            }, {
                e(it as Exception)
            })
    }

    fun switchGenericBooleanValue() {
        _isGeneric.postValue(false)
    }
}
