package com.example.mymoviestry2.Utils

import android.net.Uri
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Network_Utils {
    val baseUrl = "https://kinobd.ru/api/films"

    val PARAMS_PAGE = "page"

    fun buildURL(page: Int): URL {
        var result: URL? = null
        val uri = Uri.parse(baseUrl).buildUpon().appendQueryParameter(PARAMS_PAGE, page.toString()).build()
            result = URL(uri.toString())

        return result
    }

    fun getJSONobject(page: Int): JSONObject? {
        val url = buildURL(page)
        val result = JSONloadTask(url)
        return result
    }

    fun JSONloadTask(url: URL?): JSONObject? {
        var result: JSONObject? = null
        val stringBuilder = StringBuilder()
        val thr = Thread {
            var connection: HttpsURLConnection? = null
                try {
                    connection = url?.openConnection() as HttpsURLConnection
                    val inputStreamReader = InputStreamReader(connection.inputStream)
                    val reader = BufferedReader(inputStreamReader)
                    var line = reader.readLine()
                    while (line != null) {
                        stringBuilder.append(line)
                        line = reader.readLine()
                    }
                    result = JSONObject(stringBuilder.toString())
                } catch (e: IOException) {
                } catch (e: JSONException) {
                } finally {
                    connection?.disconnect()
                }
        }
        thr.start()
        thr.join()
        return result
    }


}