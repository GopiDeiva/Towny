package hatchure.towny.WebHandler;

import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class WebRequesthandler {
    public static final String BASE_URL = "http://shopclicks.in.md-24.webhostbox.net/ci/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}