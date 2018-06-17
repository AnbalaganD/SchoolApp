package edu.schoolapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.schoolapp.Model.PrimaryMenuDao;

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
        primaryMenuAdapter = new PrimaryMenuAdapter(primaryMenuList, this);
        primaryMenuRecyclerView.setAdapter(primaryMenuAdapter);

//        getPrimaryMenu();
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

    @Override
    protected void onResume() {
        super.onResume();
        getPrimaryMenu();
    }

    private void setupPrimaryMenu(QuerySnapshot queryDocumentSnapshot) {
        primaryMenuList = new ArrayList<>();

        if (queryDocumentSnapshot == null) {
            primaryMenuList.add(new PrimaryMenuModel(R.drawable.todo, PrimaryMenuType.TODO.toString(), PrimaryMenuType.TODO));
            primaryMenuList.add(new PrimaryMenuModel(R.drawable.assignment, PrimaryMenuType.ASSIGNMENT.toString(), PrimaryMenuType.ASSIGNMENT));
            primaryMenuList.add(new PrimaryMenuModel(R.drawable.web_link, PrimaryMenuType.WEBLINK.toString(), PrimaryMenuType.WEBLINK));
            primaryMenuList.add(new PrimaryMenuModel(R.drawable.mail, PrimaryMenuType.MAIL.toString(), PrimaryMenuType.MAIL));
            primaryMenuList.add(new PrimaryMenuModel(R.drawable.drive, PrimaryMenuType.DRIVE.toString(), PrimaryMenuType.DRIVE));
            primaryMenuList.add(new PrimaryMenuModel(R.drawable.schedule, PrimaryMenuType.TIMETABLE.toString(), PrimaryMenuType.TIMETABLE));
            primaryMenuList.add(new PrimaryMenuModel(R.drawable.time_table, PrimaryMenuType.CALENDAR.toString(), PrimaryMenuType.CALENDAR));
        } else {
            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshot) {
                PrimaryMenuDao pMenu = snapshot.toObject(PrimaryMenuDao.class);
                if (pMenu.isEnable())
                    primaryMenuList.add(getPrimaryMenuModel(pMenu));
            }
        }
        primaryMenuAdapter.updateData(primaryMenuList);
    }

    @Override
    public void onMenuSelect(PrimaryMenuModel menuModel) {
        //TODO implement your select logic here
        if (menuModel.getMenuType() == PrimaryMenuType.TODO) {
            startActivity(new Intent(this, TodoFragment.class));
        } else if (menuModel.getMenuType() == PrimaryMenuType.ASSIGNMENT) {
            startActivity(new Intent(this, AssignmentActivity.class));
        } else if (menuModel.getMenuType() == PrimaryMenuType.WEBLINK) {
            openWebLink();
        } else if (menuModel.getMenuType() == PrimaryMenuType.MAIL) {
            openGmailApp();
        } else if (menuModel.getMenuType() == PrimaryMenuType.DRIVE) {

        } else if (menuModel.getMenuType() == PrimaryMenuType.TIMETABLE) {

        } else if (menuModel.getMenuType() == PrimaryMenuType.CALENDAR) {

        }
    }

    private void openWebLink() {
        String url = "https://www.google.com/";
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true)
                .build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    private void openGmailApp() {
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        final PackageManager pm = getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") ||
                    info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
        if (best != null)
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        startActivity(intent);
    }

    private void getPrimaryMenu() {
        db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    pushDefaultPrimaryMenuIntoDatabase();
                    setupPrimaryMenu(null);
                } else
                    setupPrimaryMenu(queryDocumentSnapshots);
            }
        });
    }

    private void pushDefaultPrimaryMenuIntoDatabase() {
        CollectionReference primaryMenus = db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus");
        primaryMenus.document("todo").set(new PrimaryMenuDao("Todo", "TODO", true));
        primaryMenus.document("assignment").set(new PrimaryMenuDao("Assignment", "ASSIGNMENT", true));
        primaryMenus.document("weblink").set(new PrimaryMenuDao("WebLink", "WEBLINK", true));
        primaryMenus.document("mail").set(new PrimaryMenuDao("Mail", "MAIL", true));
        primaryMenus.document("drive").set(new PrimaryMenuDao("Drive", "DRIVE", true));
        primaryMenus.document("timetable").set(new PrimaryMenuDao("Timetable", "TIMETABLE", true));
        primaryMenus.document("calendar").set(new PrimaryMenuDao("Calendar", "CALENDAR", true));
    }

    private PrimaryMenuModel getPrimaryMenuModel(PrimaryMenuDao dao) {
        PrimaryMenuModel primaryMenuModel = null;
        switch (dao.getType()) {
            case "TODO":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.todo, "TODO", PrimaryMenuType.TODO);
                break;
            case "ASSIGNMENT":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.assignment, "ASSIGNMENT", PrimaryMenuType.ASSIGNMENT);
                break;
            case "WEBLINK":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.web_link, "WEBLINK", PrimaryMenuType.WEBLINK);
                break;
            case "MAIL":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.mail, "MAIL", PrimaryMenuType.MAIL);
                break;
            case "DRIVE":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.drive, "DRIVE", PrimaryMenuType.DRIVE);
                break;
            case "TIMETABLE":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.schedule, "TIMETABLE", PrimaryMenuType.TIMETABLE);
                break;
            case "CALENDAR":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.time_table, "CALENDAR", PrimaryMenuType.CALENDAR);
                break;
        }
        return primaryMenuModel;
    }
}
