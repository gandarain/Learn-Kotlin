package com.example.jsondemo

import android.app.Dialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CallApiLoginAsyncTask("ganda", "123456").execute()
    }

    private inner class CallApiLoginAsyncTask(val username: String, val password: String): AsyncTask<Any, Void, String>() {
        private lateinit var customProgressDialog: Dialog

        // TODO on request start
        override fun onPreExecute() {
            super.onPreExecute()
            showProgressDialog()
        }

        override fun doInBackground(vararg params: Any?): String {
            var result: String
            var connection: HttpURLConnection? = null

            try {
                val url = URL("https://run.mocky.io/v3/eb4fd6b3-7979-41f9-b076-464a1d312bbb")
                connection = url.openConnection() as HttpURLConnection
                // doInput get data
                connection.doInput = true
                // doOutput send data
                connection.doOutput = true

                connection.instanceFollowRedirects = false

                // TODO set Request method
                // POST, GET, PATCH, etc
                connection.requestMethod = "POST"

                // TODO Set connection property
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("charset", "utf-8")
                connection.setRequestProperty("Accept", "application/json")

                connection.useCaches = false

                // TODO Write body POST request
                val writeDataOutputStream = DataOutputStream(connection.outputStream)
                val jsonRequest = JSONObject()
                jsonRequest.put("username", username)
                jsonRequest.put("password", password)

                // TODO Flush and close the output stream
                writeDataOutputStream.writeBytes(jsonRequest.toString())
                writeDataOutputStream.flush()
                writeDataOutputStream.close()

                // TODO Read response
                val httpResult: Int = connection.responseCode

                // TODO check the response code, HTTP_OK ==> response code 200
                if (httpResult == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String? = null

                    try {
                        // TODO read all the response as a string
                        while (reader.readLine().also { line = it } != null) {
                            stringBuilder.append(line + "\n")
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        try {
                            inputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }

                    result = stringBuilder.toString()
                } else {
                    result = connection.responseMessage
                }
            } catch (e: SocketTimeoutException) {
                result = "Connection Timeout"
            } catch (e: Exception) {
                result = "Error : " + e.message
            } finally {
                connection?.disconnect()
            }

            return result
        }

        // TODO JSON Parse
        // on request end
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            cancelProgressDialog()
            Log.i("Json Response Result ", result!!)

            val jsonObject = JSONObject(result)
            val message: String = jsonObject.optString("message")
            Log.i("message ", message)
            val userId: Int = jsonObject.optInt("user_id")
            Log.i("userId ", "$userId")
            val name: String = jsonObject.optString("name")
            Log.i("name ", name)
            val email: String = jsonObject.optString("email")
            Log.i("email ", email)
            val mobile: Int = jsonObject.optInt("mobile")
            Log.i("mobile ", "$mobile")

            val profileDetailObject: JSONObject = jsonObject.optJSONObject("profile_details")
            val isProfileCompleted = profileDetailObject.optBoolean("is_profile_completed")
            Log.i("isProfileCompleted ", "$isProfileCompleted")
            val rating = profileDetailObject.optInt("rating")
            Log.i("rating ", "$rating")

            val dataListArray: JSONArray = jsonObject.optJSONArray("data_list")
            Log.i("dataListArray length ", "${dataListArray.length()}")

            for (item in 0 until dataListArray.length()) {
                Log.i("dataListArray length ", "${dataListArray[item]}")
                val dataItemObject: JSONObject = dataListArray[item] as JSONObject

                val id: Int = dataItemObject.optInt("id")
                Log.i("dataListArray id ", "$id")

                val value: String = dataItemObject.optString("value")
                Log.i("dataListArray value ", value)
            }
        }

        private fun showProgressDialog() {
            this.customProgressDialog = Dialog(this@MainActivity)
            this.customProgressDialog.setContentView(R.layout.dialog_custom_progress)
            this.customProgressDialog.show()
        }

        private fun cancelProgressDialog() {
            this.customProgressDialog.cancel()
        }
    }

}