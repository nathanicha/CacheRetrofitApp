package com.natlwd.cacheretrofitapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.natlwd.cacheretrofitapp.R
import com.natlwd.cacheretrofitapp.model.UserResponse
import com.natlwd.cacheretrofitapp.remote.ApiClient
import com.natlwd.cacheretrofitapp.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
*  from example: https://www.journaldev.com/13639/retrofit-android-example-tutorial
*/
class MainActivity : AppCompatActivity() {

    private var apiService: ApiService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiService = ApiClient(this).getClient()?.create(ApiService::class.java)

        setUpOnClick()
    }

    private fun setUpOnClick() {
        val callApiBtn = findViewById<AppCompatButton>(R.id.callApiBtn)
        val edt = findViewById<AppCompatEditText>(R.id.edt)
        callApiBtn.setOnClickListener {
            if (edt.text.toString().isBlank()) {
                edt.error = "Please insert user id!"
            } else {
                getUser(edt.text.toString())
            }
        }
    }

    private fun getUser(userId: String) {
        val call = apiService?.getUser(userId)
        call?.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                val result = StringBuilder()
                if (response.raw().networkResponse != null) {
                    result.append("Response from Network")
                }

                if (response.raw().cacheResponse != null) {
                    result.append("Response from CACHE")
                }

                result.append("\n")
                result.append("onResponse: " + response.body())

                val tv = findViewById<AppCompatTextView>(R.id.tv)
                tv.text = result.toString()
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                val tv = findViewById<AppCompatTextView>(R.id.tv)
                tv.text = "onFailure: ${t.message}"
                call.cancel()
            }
        })
    }
}