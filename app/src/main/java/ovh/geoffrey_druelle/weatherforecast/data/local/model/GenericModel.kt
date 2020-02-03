package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Generic")
data class GenericModel(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var var1: String,
    var var2: String?
) {

    // Not required second constructor
    @Ignore
    constructor() : this(
        id = 0,
        var1 = "",
        var2 = ""
    )
}
