package gr.dipae.thesisfitnessapp.framework.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
) : Interceptor {

    companion object {
        private const val API_KEY = "api_key"
        private const val API_KEY_VALUE = "tTddsmAtUyRwujscc1VWvcDId2d4YQrCioqcIQB1"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder().addQueryParameter(API_KEY, API_KEY_VALUE).build()
        val finalRequest = originalRequest.newBuilder().url(url).build()
        return chain.proceed(finalRequest)
    }
}