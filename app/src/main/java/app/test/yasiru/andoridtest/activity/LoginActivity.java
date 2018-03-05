package app.test.yasiru.andoridtest.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import app.test.yasiru.andoridtest.R;
import app.test.yasiru.andoridtest.dialog.AuthenticationDialog;
import app.test.yasiru.andoridtest.listener.AuthenticationListener;
import app.test.yasiru.andoridtest.presenter.LoginPresenter;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements
        LoginPresenter.View,AuthenticationListener {


    private AuthenticationDialog mAuthenticationDialog;
    private LoginPresenter mLoginPresenter;
    private ArrayList<String> mUrlList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //bind butterknife
        ButterKnife.bind(this);
        mLoginPresenter =new LoginPresenter();
        mLoginPresenter.setView(this);
        //check for internet
        if(!mLoginPresenter.isInternetAvailable(this))
          return;
        //if internet available, start  webview to authenticate
        mAuthenticationDialog = new AuthenticationDialog(this, this);
        mAuthenticationDialog.show();


    }

    //after authentication finish get access token
    @Override
    public void onCodeReceived(String accessToken) {
        if (accessToken == null) {
            mAuthenticationDialog.dismiss();
        }
        else{ //get  image url list
            mLoginPresenter.fetchData(accessToken, mUrlList);
        }

    }

    //if something get wrong display error message
    @Override
    public void displayError(String errorMsg) {
        Toast.makeText(this,errorMsg,Toast.LENGTH_LONG).show();
    }

    // receive  image url list
    @Override
    public void renderGrid(ArrayList<String> arrayList) {
        Log.i("TAG", " " +arrayList.size());

        //display images
        Intent intent = new Intent(this, ImageActivity.class);
        //pass image url list
        intent.putStringArrayListExtra(getResources().getString(R.string.url_list), arrayList);
        startActivity(intent);

    }



    @Override
    public Context context() {
        return this;
    }


}
