package id.asad.githubuserfinder.api

import id.asad.githubuserfinder.BuildConfig
import id.asad.githubuserfinder.model.ResponseUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN_GITHUB}")
    fun findUserByUsername(@Query("q") username : String) : Call<ResponseUsers>

}