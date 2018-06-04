package edu.schoolapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private NotificationAdapter notificationAdapter;
    private List<NotificationModel> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.close);
        actionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView notificationRecyclerView = findViewById(R.id.notification_recycler_view);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        addDummyData();
        notificationAdapter = new NotificationAdapter(notificationList);
        notificationRecyclerView.setAdapter(notificationAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void getNotification() {
        //TODO implement api for get remote notification
    }

    private void addDummyData() {
        NotificationModel item1 = new NotificationModel("Nice, This is test notification", "just now");
        NotificationModel item2 = new NotificationModel("Nice, This is test notification", "just now");
        NotificationModel item3 = new NotificationModel("Nice, This is test notification", "just now");
        NotificationModel item4 = new NotificationModel("Nice, This is test notification", "just now");
        NotificationModel item5 = new NotificationModel("Nice, This is test notification", "just now");

        notificationList.add(item1);
        notificationList.add(item2);
        notificationList.add(item3);
        notificationList.add(item4);
        notificationList.add(item5);
    }
}
