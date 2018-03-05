package app.test.yasiru.andoridtest.presenter;


import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import app.test.yasiru.andoridtest.R;
import app.test.yasiru.andoridtest.POJO.InstagramResponse;
import app.test.yasiru.andoridtest.rest.RestClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by yasiru on 3/1/18.
 */

public class LoginPresenter extends Presenter<LoginPresenter.View> {

    @Override public void terminate() {
        super.terminate();
        setView(null);
    }


    //check for internet
    public boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting())
            return  true;
        else {
            //display error message
            getView().displayError(context.getResources().getString(R.string.net_error));
            return  false;
        }

    }


    //get image url and store in array list
    public void fetchData(String accessToken,final ArrayList<String> urlList) {

        Call<InstagramResponse> call = RestClient.getRetrofitService().getTagPhotos(accessToken);
        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {

                //if response is not null collect the image url
                if (response.body() != null) {
                    for(int i = 0; i < response.body().getData().length; i++){
                        urlList.add(response.body().getData()[i].getImages().getStandard_resolution().getUrl());
                    }
                    Log.i("TAG", " " +urlList.size());
                    //set image url array
                    getView().renderGrid(urlList);
                }
            }

            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {
                //Handle failure
                 getView().displayError(t.toString());
            }
        });
    }



    public interface View extends Presenter.View {



        void displayError(String errorMsg);

        void renderGrid(ArrayList<String> urlList);


    }
}
