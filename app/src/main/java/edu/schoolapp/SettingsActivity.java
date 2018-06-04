package edu.schoolapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class SettingsActivity extends AppCompatActivity {

    private ImageView userImageView;
    private TextView userNameTextView;
    private TextView emailTextView;
    private TextView managePrimaryMenuTextView;
    private TextView logoutTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userImageView = findViewById(R.id.setting_user_image_view);
        userNameTextView = findViewById(R.id.settings_user_name_text_view);
        emailTextView = findViewById(R.id.settings_email_text_view);
        managePrimaryMenuTextView = findViewById(R.id.manage_text_view);
        logoutTextView = findViewById(R.id.logout_text_view);

        new DownloadImageAsync(userImageView).execute(SchoolApp.getProfileUrl());
        userNameTextView.setText(SchoolApp.getName());
        emailTextView.setText(SchoolApp.getEmail());

        managePrimaryMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ManageListFragment.class));
            }
        });

        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


    //Inner Class
    class DownloadImageAsync extends AsyncTask<String, Void, Bitmap> {

        private ImageView userImage;

        DownloadImageAsync(ImageView userImage) {
            this.userImage = userImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(new URL(urls[0]).openStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            userImage.setImageDrawable(roundedBitmapDrawable);
        }
    }
}
