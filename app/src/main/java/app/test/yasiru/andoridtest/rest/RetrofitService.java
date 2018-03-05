package app.test.yasiru.andoridtest.rest;




import app.test.yasiru.andoridtest.POJO.InstagramResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RetrofitService {
     //service
    @GET("v1/users/self/media/recent")
    Call<InstagramResponse> getTagPhotos(@Query("access_token") String access_token);
}

