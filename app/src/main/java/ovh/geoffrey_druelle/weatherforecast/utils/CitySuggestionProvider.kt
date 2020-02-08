package ovh.geoffrey_druelle.weatherforecast.utils

import android.app.SearchManager
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.BaseColumns
import okhttp3.OkHttpClient
import org.json.JSONArray


class CitySuggestionProvider: ContentProvider() {

    private val AUTHORITY = "ovh.geoffrey_druelle.weatherforecast.utils.citysuggestion"

    private val TYPE_ALL_SUGGESTIONS = 1
    private val TYPE_SINGLE_SUGGESTION = 2

    private var uriMatcher: UriMatcher? = null
    private var cities: MutableList<String>? = null

    override fun onCreate(): Boolean {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher!!.addURI(AUTHORITY, "/#", TYPE_SINGLE_SUGGESTION)
        uriMatcher!!.addURI(AUTHORITY, "search_suggest_query/*", TYPE_ALL_SUGGESTIONS)
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String?>?, selection: String?,
        selectionArgs: Array<String?>?, sortOrder: String?
    ): Cursor? {
        if (cities == null || cities!!.isEmpty()) {
            Log.d("NGVL", "WEB")
            val client = OkHttpClient()
            val request: Request = Builder()
                .url("https://dl.dropboxusercontent.com/u/6802536/cidades.json")
                .build()
            try {
                val response: Response = client.newCall(request).execute()
                val jsonString: String = response.body().string()
                val jsonArray = JSONArray(jsonString)
                cities = ArrayList()
                val lenght = jsonArray.length()
                for (i in 0 until lenght) {
                    val city = jsonArray.getString(i)
                    cities.add(city)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Log.d("NGVL", "Cache!")
        }
        val cursor = MatrixCursor(
            arrayOf(
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
            )
        )
        if (uriMatcher!!.match(uri) == TYPE_ALL_SUGGESTIONS) {
            if (cities != null) {
                val query = uri.lastPathSegment!!.toUpperCase()
                val limit =
                    uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT)!!.toInt()
                val lenght = cities!!.size
                var i = 0
                while (i < lenght && cursor.count < limit) {
                    val city = cities!![i]
                    if (city.toUpperCase().contains(query)) {
                        cursor.addRow(arrayOf(i, city, i))
                    }
                    i++
                }
            }
        } else if (uriMatcher!!.match(uri) == TYPE_SINGLE_SUGGESTION) {
            val position = uri.lastPathSegment!!.toInt()
            val city = cities!![position]
            cursor.addRow(arrayOf(position, city, position))
        }
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }
}