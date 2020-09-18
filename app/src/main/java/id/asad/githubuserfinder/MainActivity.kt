package id.asad.githubuserfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.asad.githubuserfinder.api.ApiConfig
import id.asad.githubuserfinder.model.ResponseUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiConfig.getInstance().findUserByUsername("asad").enqueue(object : Callback<ResponseUsers> {
            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                Log.d("MainActivity", t.localizedMessage)
            }
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                if (response.isSuccessful){
                    val responseUsers = response.body()
                    val items = responseUsers?.items

                    items?.forEach {
                        Log.d("MainActivity", it?.login ?: "")
                    }
                }
            }
        })
    }
}