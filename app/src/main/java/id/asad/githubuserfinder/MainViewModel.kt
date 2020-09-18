package id.asad.githubuserfinder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.asad.githubuserfinder.api.ApiConfig
import id.asad.githubuserfinder.model.ItemsItem
import id.asad.githubuserfinder.model.ResponseUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<ItemsItem?>?>()
    private val error = MutableLiveData<String?>()
    private val responseCode = MutableLiveData<Int?> ()

    fun findListUsers(user : String){
        ApiConfig.getInstance().findUserByUsername(user).enqueue(object : Callback<ResponseUsers> {
            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                error.postValue(t.localizedMessage)
                Log.d("MainActivity", t.localizedMessage)
            }
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                if (response.isSuccessful){
                    val code = response.code()
                    responseCode.postValue(code)

                    if (code == 200){
                        val result = response.body()?.items
                        listUsers.postValue(result as ArrayList<ItemsItem?>)
                    }
                }
            }
        })
    }

    fun getListUsers() : LiveData<ArrayList<ItemsItem?>?>{
        return listUsers
    }

    fun getError() : LiveData<String?>{
        return error
    }

    fun getResponseCode() : LiveData<Int?>{
        return responseCode
    }
}