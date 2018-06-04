//package edu.schoolapp;
//
//import android.app.FragmentTransaction;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.NavigationView;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Dictionary;
//import java.util.Hashtable;
//
//public class NavigationDrawerActivity extends AppCompatActivity {
//
//    private DrawerLayout drawerLayout;
//    private ImageView userImageView;
//    private TextView nameTextView;
//    private TextView emailTextView;
//    private Dictionary<String, Integer> sideMenuList = new Hashtable<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_navigation_drawer);
//
////        Toolbar toolbar = findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.menu);
//
//        drawerLayout = findViewById(R.id.navigation_drawer_layout);
//        NavigationView navigationView = findViewById(R.id.navigation_view);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                onNavigationMenuSelected(item.getItemId());
//                closeNavigationDrawer();
//                return false;
//            }
//        });
//
//        View headerView = navigationView.getHeaderView(0);
//        userImageView = headerView.findViewById(R.id.user_image_view);
//        nameTextView = headerView.findViewById(R.id.user_name_text_view);
//        emailTextView = headerView.findViewById(R.id.email_text_view);
//
//        new NavigationDrawerActivity.DownloadImageAsync(userImageView).execute(SchoolApp.getProfileUrl());
//        nameTextView.setText(SchoolApp.getName());
//        emailTextView.setText(SchoolApp.getEmail());
//
//        headerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
////        navigationView.getMenu().clear();
////        int id = View.generateViewId();
////        sideMenuList.put("Home", id);
////        navigationView.getMenu().add(0, id, 0, "Home");
//
//        HomeFragment homeFragment = new HomeFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.content_frame, homeFragment).commit();
//    }
//
//    public void openNavigationDrawer() {
//        drawerLayout.openDrawer(Gravity.LEFT);
//    }
//
//    public void closeNavigationDrawer() {
//        drawerLayout.closeDrawer(Gravity.START);
//    }
//
//    private void onNavigationMenuSelected(int id) {
//        if (id == R.id.todo_menu_item) {
//            TodoFragment todoFragment = new TodoFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.content_frame, todoFragment).commit();
//        } else if (id == R.id.assignment_menu_item) {
//            TodoFragment todoFragment = new TodoFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.content_frame, todoFragment).commit();
//        } else if (id == R.id.weblink_menu_item) {
//            TodoFragment todoFragment = new TodoFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.content_frame, todoFragment).commit();
//        } else if (id == R.id.mail_menu_item) {
//
//        } else if (id == R.id.drive_menu_item) {
//
//        } else if (id == R.id.timetable_menu_item) {
//
//        } else if (id == R.id.calendar_menu_item) {
//
//        }
//
////        else if (id == sideMenuList.get("Home")) {
////            Log.d("", "onNavigationMenuSelected: ");
////        }
//    }
//
//    private void setupMenu() {
//
//    }
//
//    class DownloadImageAsync extends AsyncTask<String, Void, Bitmap> {
//
//        private ImageView userImage;
//
//        DownloadImageAsync(ImageView userImage) {
//            this.userImage = userImage;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... urls) {
//            Bitmap bitmap = null;
//            try {
//                bitmap = BitmapFactory.decodeStream(new URL(urls[0]).openStream());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        protected void onPostExecute(Bitmap bitmap) {
//            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//            roundedBitmapDrawable.setCircular(true);
//            userImage.setImageDrawable(roundedBitmapDrawable);
//        }
//    }
//}