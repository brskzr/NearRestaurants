package com.brskzr.nearrestaurants.infrastructure.utils

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


object InternetUtils {

    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network?.isConnected) ?: false
    }

    fun checkInternetConnection(context: Context, success: () -> Unit, error: ()-> Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                if (hasNetworkAvailable(context)) {
                    try {
                        val connection = URL("https://www.google.com").openConnection() as HttpURLConnection
                        connection.setRequestProperty("User-Agent", "Test")
                        connection.setRequestProperty("Connection", "close")
                        connection.connectTimeout = 5000
                        connection.connect()

                        if(connection.responseCode == 200){
                            withContext(Dispatchers.Main) {
                                success.invoke()
                            }
                        }
                        else {
                            withContext(Dispatchers.Main) {
                                error.invoke()
                            }
                        }
                    } catch (e: IOException) {
                        withContext(Dispatchers.Main) {
                            error.invoke()
                        }
                    }
                }
                else {
                    withContext(Dispatchers.Main) {
                        error.invoke()
                    }
                }

            }
            catch (ex:Exception) {
                withContext(Dispatchers.Main) {
                    error.invoke()
                }
            }
        }
    }
}
