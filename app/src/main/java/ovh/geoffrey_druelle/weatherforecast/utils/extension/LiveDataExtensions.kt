package ovh.geoffrey_druelle.weatherforecast.utils.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.obs(lifecycleOwner: LifecycleOwner, onChanged: (T) -> Unit) {
    observe(lifecycleOwner, Observer {
        it ?: return@Observer
        onChanged.invoke(it)
    })
}