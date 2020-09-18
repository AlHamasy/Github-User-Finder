package id.asad.githubuserfinder.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import id.asad.githubuserfinder.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private fun  getInterceptor() : OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.apply {
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    private fun initGson() : Gson {
        return GsonBuilder().setLenient().create()
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create(initGson()))
            .client(getInterceptor())
            .build()
    }

    fun getInstance() : ApiService{
        return getRetrofit().create(ApiService::class.java)
    }

}