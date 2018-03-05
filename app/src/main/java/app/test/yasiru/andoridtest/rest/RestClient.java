package app.test.yasiru.andoridtest.rest;


import app.test.yasiru.andoridtest.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient {

    //create reset client
    public static RetrofitService getRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService.class);
    }
}