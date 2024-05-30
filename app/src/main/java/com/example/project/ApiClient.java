package com.example.project;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String BASE_URL = "https://newsapi.org/v2/";

    private static Retrofit retrofit;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
        retrofit = null;
    }

    public static String getApiKey() {
        return "cbf68b2ce45741c1aad72faf4efe7407";
    }
}
