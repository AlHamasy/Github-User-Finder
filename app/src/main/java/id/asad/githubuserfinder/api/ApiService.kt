package id.asad.githubuserfinder.api

import id.asad.githubuserfinder.model.ResponseUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun findUserByUsername(@Query("q") username : String,
                            @Header("Authorization") token : String) : Call<ResponseUsers>


}