package app.test.yasiru.andoridtest.activity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import app.test.yasiru.andoridtest.R;


import java.util.ArrayList;

import app.test.yasiru.andoridtest.adapter.RecyclerViewAdapter;
import app.test.yasiru.andoridtest.presenter.ImagePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity implements
        RecyclerViewAdapter.ItemClickListener,ImagePresenter.View {

    private ArrayList<String> mArrayList = new ArrayList<String>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private ImagePresenter mImagePresenter;
    private int mColumnCount =0;
    @BindView(R.id.recycle_view_grid_image)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);


        mImagePresenter=new ImagePresenter();
        mImagePresenter.setView(this);
        //receive image url list
        mArrayList= getIntent().getStringArrayListExtra(getResourceString(R.string.url_list));
        Log.i("TAG", " count " +mArrayList.size());
        //display grid
        displayGrid();

    }




    void displayGrid(){
        //get column number according to display size
        mImagePresenter.CalColumnCount(this);
        recyclerViewAdapter = new RecyclerViewAdapter(this,mArrayList);
        recyclerViewAdapter.setClickListener(this);
        //set grid layout
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        mRecyclerView.setAdapter(recyclerViewAdapter);
        Log.i("TAG", "column count" +mColumnCount  + "list size"+ mArrayList.size() );

    }





    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG",  " which is at cell position " + position);

        // Sending image id to FullScreenActivity
        Intent intent = new Intent(getApplicationContext(), FullImageActivity.class);
        // passing array index
        String url = mArrayList.get(position);
        //pass url
        intent.putExtra(getResourceString(R.string.url), url);
        //add scale up animation
        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                0, view.getWidth(), view.getHeight());
        startActivity(intent,options.toBundle());
    }



    @Override
    public Context context() {
        return this;
    }


    //add menu -sign out button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //menu button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out: {
                // sign-out stuff
                confirmSignOut();
                break;            }
        }
        return false;
    }


    void confirmSignOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(getResources().getString(R.string.confirm_dialog_title));
        builder.setMessage(getResources().getString(R.string.confirm_dialog_message));
        builder.setPositiveButton(getResourceString(R.string.confirm_dialog_text),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     //user click yes ,sign out
                     signOut();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //noting no click
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void signOut(){
        // delete cookies in web view and remove save login details
        CookieManager.getInstance().removeAllCookie();
        // clear list view and refresh grid view
        mArrayList.clear();
        recyclerViewAdapter.notifyDataSetChanged();

        Intent intent = new Intent(ImageActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    // when back button press instead of going to login activity
    // minimize the app
    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    // get number of column
    @Override
    public void setColumnCount(int columnCount) {
        mColumnCount =columnCount;
    }

    //return string value of string.xml
    String getResourceString(int id){
        return  this.getResources().getString(id);
    }
}
