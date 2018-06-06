package edu.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends AppCompatActivity implements PrimaryMenuSelectListener {

    private PrimaryMenuAdapter primaryMenuAdapter;
    private List<PrimaryMenuModel> primaryMenuList;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        db = FirebaseFirestore.getInstance();

        RecyclerView primaryMenuRecyclerView = findViewById(R.id.primary_menu_recycler_view);
        primaryMenuRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        setupPrimaryMenu();
        primaryMenuAdapter = new PrimaryMenuAdapter(primaryMenuList, this);
        primaryMenuRecyclerView.setAdapter(primaryMenuAdapter);

//        db.collection("users").document("ZQa8vvMf7FR8kul0slQz").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    User user = documentSnapshot.toObject(User.class);
//                }
//            }
//        });

        getPrimaryMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.notification_menu_item) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupPrimaryMenu() {
        primaryMenuList = new ArrayList<>();
        primaryMenuList.add(new PrimaryMenuModel(R.drawable.todo, PrimaryMenuType.TODO.toString(), PrimaryMenuType.TODO));
        primaryMenuList.add(new PrimaryMenuModel(R.drawable.assignment, PrimaryMenuType.ASSIGNMENT.toString(), PrimaryMenuType.ASSIGNMENT));
        primaryMenuList.add(new PrimaryMenuModel(R.drawable.web_link, PrimaryMenuType.WEBLINK.toString(), PrimaryMenuType.WEBLINK));
        primaryMenuList.add(new PrimaryMenuModel(R.drawable.mail, PrimaryMenuType.MAIL.toString(), PrimaryMenuType.MAIL));
        primaryMenuList.add(new PrimaryMenuModel(R.drawable.drive, PrimaryMenuType.DRIVE.toString(), PrimaryMenuType.DRIVE));
        primaryMenuList.add(new PrimaryMenuModel(R.drawable.schedule, PrimaryMenuType.TIMETABLE.toString(), PrimaryMenuType.TIMETABLE));
        primaryMenuList.add(new PrimaryMenuModel(R.drawable.time_table, PrimaryMenuType.CALENDAR.toString(), PrimaryMenuType.CALENDAR));
    }

    @Override
    public void onMenuSelect(PrimaryMenuModel menuModel) {
        //TODO implement your select logic here
        if (menuModel.getMenuType() == PrimaryMenuType.TODO) {
            startActivity(new Intent(this, TodoFragment.class));
        } else if (menuModel.getMenuType() == PrimaryMenuType.ASSIGNMENT) {

        } else if (menuModel.getMenuType() == PrimaryMenuType.WEBLINK) {

        } else if (menuModel.getMenuType() == PrimaryMenuType.MAIL) {

        } else if (menuModel.getMenuType() == PrimaryMenuType.DRIVE) {

        } else if (menuModel.getMenuType() == PrimaryMenuType.TIMETABLE) {

        } else if (menuModel.getMenuType() == PrimaryMenuType.CALENDAR) {

        }
    }

    private void getPrimaryMenu() {
        db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    pushDefaultPrimaryMenuIntoDatabase();
                }
                setupPrimaryMenu();
            }
        });
    }

    private void pushDefaultPrimaryMenuIntoDatabase() {

        Map<String, Object> primaryMenus = new HashMap<>();
        primaryMenus.put("todo", "Todo");
        primaryMenus.put("assignment", "Assignment");
        primaryMenus.put("web_link", "Web Link");
        primaryMenus.put("mail", "Mail");
        primaryMenus.put("drive", "Drive");
        primaryMenus.put("timetable", "Timetable");
        primaryMenus.put("calender", "Calender");

//        List<String> primaryMenus = new ArrayList<>();
//        primaryMenus.add("Todo");
//        primaryMenus.add("Assignment");
//        primaryMenus.add("WebLink");
//        primaryMenus.add("Drive");
//        primaryMenus.add("Timetable");
//        primaryMenus.add("Calender");


//        for (int i = 0; i < primaryMenus.size(); i++ ) {
            db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus").add(primaryMenus);
//        }
    }
}
