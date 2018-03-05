package app.test.yasiru.andoridtest.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import app.test.yasiru.andoridtest.Constants;
import app.test.yasiru.andoridtest.R;
import app.test.yasiru.andoridtest.listener.AuthenticationListener;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by yasiru on 2/28/18.
 */

public class AuthenticationDialog extends Dialog {

    private final AuthenticationListener mAuthenticationListener;
    private Context mContext;
    private ProgressDialog mSpinner;
    @BindView(R.id.web_view)
    WebView mWebView;

    private final String url ;
    // data is passed into the constructor
    public AuthenticationDialog(@NonNull Context context, AuthenticationListener authenticationListener) {
        super(context);
        this.mContext = context;
        this.mAuthenticationListener = authenticationListener;

        // url to api call
        url = Constants.BASE_URL
                + getResourceString(R.string.url_auth_part)
                + Constants.CLIENT_ID
                +  getResourceString(R.string.url_redirect_part)
                + Constants.REDIRECT_URI
                +  getResourceString(R.string.url_other_part);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.auth_dialog);
        ButterKnife.bind(this);
        //loading spinner
        mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage( getResourceString(R.string.loading_text));
        initializeWebView();
    }

    //initialize web view to show login page
    private void initializeWebView() {
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            boolean authComplete = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mSpinner.show();
            }

            String access_token;

            //when page finish get result
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // if success get access token
                if (url.contains(getResourceString(R.string.url_acess_token_part))
                        && !authComplete) {
                    Uri uri = Uri.parse(url);
                    access_token = uri.getEncodedFragment();
                    // get the whole token after the '=' sign
                    access_token = access_token.substring(access_token.lastIndexOf("=")+1);
                    Log.i("", "CODE : " + access_token);
                    authComplete = true;
                    //after success call code recvied method on activity
                    mAuthenticationListener.onCodeReceived(access_token);
                    dismiss();
                   //check for error
                } else if (url.contains(getResourceString(R.string.url_error_part))) {
                    Toast.makeText(mContext, getResourceString(R.string.error), Toast.LENGTH_SHORT).show();
                    dismiss();
                }
                // user name password error instragram api try to login via facebook
                else if (url.equals(getResourceString(R.string.url_instragram))
                || url.equals(getResourceString(R.string.url_instragram_login))){
                    initializeWebView();
                    Toast.makeText(mContext, getResourceString(R.string.login_error), Toast.LENGTH_SHORT).show();

                }

                Log.d(TAG, "onPageFinished URL: " + url);
                mSpinner.dismiss();
            }
        });
    }

    //get resource string in string.xml
     String getResourceString(int id){
        return  mContext.getResources().getString(id);
     }

}
