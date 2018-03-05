package app.test.yasiru.andoridtest.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import app.test.yasiru.andoridtest.R;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FullImageActivity extends AppCompatActivity {

    @BindView(R.id.full_image_view)
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        ButterKnife.bind(this);
        //get image url
        String url = getIntent().getExtras().getString(getResources().getString(R.string.url));
        //fade animation alpha 0 to 1
        mImageView.setAlpha(0f);
        Picasso.with(this)
                .load(url)
                .into(mImageView);
        mImageView.animate().setDuration(1000).alpha(1f).start();

    }



}
