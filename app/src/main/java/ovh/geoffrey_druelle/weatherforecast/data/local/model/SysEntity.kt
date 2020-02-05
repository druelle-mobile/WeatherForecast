package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Sys

@Entity
data class SysEntity(
    var pod: String
) {

    @Ignore
    constructor(sys: Sys) : this(
        pod = sys.pod
    )
}