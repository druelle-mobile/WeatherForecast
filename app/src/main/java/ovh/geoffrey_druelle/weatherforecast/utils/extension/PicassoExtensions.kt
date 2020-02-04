package ovh.geoffrey_druelle.weatherforecast.utils.extension

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun paint(path: String, imageView: ImageView) {
    Picasso.get().load(path).into(imageView)
}