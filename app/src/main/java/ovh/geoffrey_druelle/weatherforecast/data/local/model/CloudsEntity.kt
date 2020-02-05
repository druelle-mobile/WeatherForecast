package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Clouds

@Entity
data class CloudsEntity(
    var all: Int
) {

    @Ignore
    constructor(clouds: Clouds) : this(
        all = clouds.all
    )
}