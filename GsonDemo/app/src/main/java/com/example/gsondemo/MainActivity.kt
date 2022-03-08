package com.example.gsondemo

import android.app.Dialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
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

        /**
         * This function will be used to perform background execution.
         */
        override fun doInBackground(vararg params: Any?): String {
            var result: String
            /**
             * https://developer.android.com/reference/java/net/HttpURLConnection
             *
             * You can use the above url for Detail understanding of HttpURLConnection class
             */
            var connection: HttpURLConnection? = null

            try {
                val url = URL("https://run.mocky.io/v3/eb4fd6b3-7979-41f9-b076-464a1d312bbb")
                connection = url.openConnection() as HttpURLConnection

                /**
                 * A URL connection can be used for input and/or output.  Set the DoOutput
                 * flag to true if you intend to use the URL connection for output,
                 * false if not.  The default is false.
                 */
                // doInput get data
                connection.doInput = true
                // doOutput send data
                connection.doOutput = true

                /**
                 * Sets whether HTTP redirects should be automatically followed by this instance.
                 * The default value comes from followRedirects, which defaults to true.
                 */
                connection.instanceFollowRedirects = false

                /**
                 * Set the method for the URL request, one of:
                 *  GET
                 *  POST
                 *  HEAD
                 *  OPTIONS
                 *  PUT
                 *  DELETE
                 *  TRACE
                 *  are legal, subject to protocol restrictions.  The default method is GET.
                 */
                // TODO set Request method
                // POST, GET, PATCH, etc
                connection.requestMethod = "POST"

                /**
                 * Sets the general request property. If a property with the key already
                 * exists, overwrite its value with the new value.
                 */
                // TODO Set connection property
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("charset", "utf-8")
                connection.setRequestProperty("Accept", "application/json")

                /**
                 * Some protocols do caching of documents.  Occasionally, it is important
                 * to be able to "tunnel through" and ignore the caches (e.g., the
                 * "reload" button in a browser).  If the UseCaches flag on a connection
                 * is true, the connection is allowed to use whatever caches it can.
                 *  If false, caches are to be ignored.
                 *  The default value comes from DefaultUseCaches, which defaults to
                 * true.
                 */
                connection.useCaches = false

                /**
                 * Creates a new data output stream to write data to the specified
                 * underlying output stream. The counter written is set to zero.
                 */
                // TODO Write body POST request
                // Create JSONObject Request
                val writeDataOutputStream = DataOutputStream(connection.outputStream)
                val jsonRequest = JSONObject()
                jsonRequest.put("username", username)
                jsonRequest.put("password", password)

                /**
                 * Writes out the string to the underlying output stream as a
                 * sequence of bytes. Each character in the string is written out, in
                 * sequence, by discarding its high eight bits. If no exception is
                 * thrown, the counter written is incremented by the
                 * length of s.
                 */
                // TODO Flush and close the output stream
                writeDataOutputStream.writeBytes(jsonRequest.toString())
                // Flushes this data output stream.
                writeDataOutputStream.flush()
                // Closes this output stream and releases any system resources associated with the stream
                writeDataOutputStream.close()

                // TODO Read response
                val httpResult: Int = connection.responseCode

                // TODO check the response code, HTTP_OK ==> response code 200
                if (httpResult == HttpURLConnection.HTTP_OK) {
                    /**
                     * Returns an input stream that reads from this open connection.
                     */
                    val inputStream = connection.inputStream

                    /**
                     * Creates a buffering character-input stream that uses a default-sized input buffer.
                     */
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String? = null

                    try {
                        /**
                         * Reads a line of text.  A line is considered to be terminated by any one
                         * of a line feed ('\n'), a carriage return ('\r'), or a carriage return
                         * followed immediately by a linefeed.
                         */
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
                    /**
                     * Gets the HTTP response message, if any, returned along with the
                     * response code from a server.
                     */
                    result = connection.responseMessage
                }
            } catch (e: SocketTimeoutException) {
                result = "Connection Timeout"
            } catch (e: Exception) {
                result = "Error : " + e.message
            } finally {
                connection?.disconnect()
            }

            // You can notify with your result to onPostExecute.
            return result
        }

        /**
         * This function will be executed after the background execution is completed.
         */
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            cancelProgressDialog()
            Log.i("Json Response Result ", result!!)

            // Map the json response with the Data Class using GSON.
            val responseData = Gson().fromJson(result, ResponseModel::class.java)

            Log.i("Message", responseData.message)
            Log.i("User Id", "${responseData.user_id}")
            Log.i("Name", responseData.name)
            Log.i("Email", responseData.email)
            Log.i("Mobile", "${responseData.mobile}")

            // Profile Details
            Log.i("Is Profile Completed", "${responseData.profile_details.is_profile_completed}")
            Log.i("Rating", "${responseData.profile_details.rating}")

            // Data List Details.
            Log.i("Data List Size", "${responseData.data_list.size}")

            for (item in responseData.data_list.indices) {
                Log.i("Value $item", "${responseData.data_list[item]}")

                Log.i("ID", "${responseData.data_list[item].id}")
                Log.i("Value", responseData.data_list[item].value)
            }

            Toast.makeText(this@MainActivity, responseData.message, Toast.LENGTH_SHORT).show()
        }

        /**
         * Method is used to show the Custom Progress Dialog.
         */
        private fun showProgressDialog() {
            this.customProgressDialog = Dialog(this@MainActivity)
            /*Set the screen content from a layout resource.
            The resource will be inflated, adding all top-level views to the screen.*/
            this.customProgressDialog.setContentView(R.layout.dialog_custom_progress)
            // Start the dialog and display it on screen.
            this.customProgressDialog.show()
        }

        /**
         * This function is used to dismiss the progress dialog if it is visible to user.
         */
        private fun cancelProgressDialog() {
            this.customProgressDialog.cancel()
        }
    }

}