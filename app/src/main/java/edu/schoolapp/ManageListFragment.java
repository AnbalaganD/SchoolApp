package edu.schoolapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.schoolapp.Model.PrimaryMenuDao;

public class ManageListFragment extends AppCompatActivity {

    private ManageListAdapter manageListAdapter;
    private List<PrimaryMenuModel> manageList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_manage_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        ListView manageListView = findViewById(R.id.manage_list_view);
        manageListAdapter = new ManageListAdapter(manageList);
        manageListView.setAdapter(manageListAdapter);

        findViewById(R.id.manage_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference menulist = db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus");
                if (manageList != null) {
                    for (PrimaryMenuModel menuModel : manageList) {
                        PrimaryMenuDao dao = getPrimaryMenuDao(menuModel);
                        menulist.document(dao.getType().toLowerCase()).set(dao);
                    }
                }
                Toast.makeText(ManageListFragment.this, "Your list update successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        manageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PrimaryMenuModel manageListModel = manageList.get(position);
                manageListModel.setSelected(!manageListModel.isSelected());
                manageListAdapter.notifyDataSetChanged();
            }
        });

        getPrimaryManageList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

//    private void addManageList() {
//        manageList.add(new PrimaryMenuModel(R.drawable.todo, "Todo", PrimaryMenuType.TODO));
//        manageList.add(new PrimaryMenuModel(R.drawable.assignment, "Assignment", PrimaryMenuType.TODO));
//        manageList.add(new PrimaryMenuModel(R.drawable.web_link, "Web Link", PrimaryMenuType.TODO));
//        manageList.add(new PrimaryMenuModel(R.drawable.mail, "Mail", PrimaryMenuType.TODO));
//        manageList.add(new PrimaryMenuModel(R.drawable.drive, "Drive", PrimaryMenuType.TODO));
//        manageList.add(new PrimaryMenuModel(R.drawable.schedule, "TimeTable", PrimaryMenuType.TODO));
//        manageList.add(new PrimaryMenuModel(R.drawable.time_table, "Calendar", PrimaryMenuType.TODO));
//    }

    private void getPrimaryManageList() {
        db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                manageList = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        PrimaryMenuDao pMenu = snapshot.toObject(PrimaryMenuDao.class);
                        manageList.add(getPrimaryMenuModel(pMenu));
                    }
                    manageListAdapter.updateData(manageList);
                }
            }
        });
    }

    private PrimaryMenuModel getPrimaryMenuModel(PrimaryMenuDao dao) {
        PrimaryMenuModel primaryMenuModel = null;
        switch (dao.getType()) {
            case "TODO":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.todo, PrimaryMenuType.TODO.toString(), PrimaryMenuType.TODO, dao.isEnable());
                break;
            case "ASSIGNMENT":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.assignment, PrimaryMenuType.ASSIGNMENT.toString(), PrimaryMenuType.ASSIGNMENT, dao.isEnable());
                break;
            case "WEBLINK":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.web_link, PrimaryMenuType.WEBLINK.toString(), PrimaryMenuType.WEBLINK, dao.isEnable());
                break;
            case "MAIL":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.mail, PrimaryMenuType.MAIL.toString(), PrimaryMenuType.MAIL, dao.isEnable());
                break;
            case "DRIVE":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.drive, PrimaryMenuType.DRIVE.toString(), PrimaryMenuType.DRIVE, dao.isEnable());
                break;
            case "TIMETABLE":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.schedule, PrimaryMenuType.TIMETABLE.toString(), PrimaryMenuType.TIMETABLE, dao.isEnable());
                break;
            case "CALENDAR":
                primaryMenuModel = new PrimaryMenuModel(R.drawable.time_table, PrimaryMenuType.CALENDAR.toString(), PrimaryMenuType.CALENDAR, dao.isEnable());
                break;
        }
        return primaryMenuModel;
    }

    private PrimaryMenuDao getPrimaryMenuDao(PrimaryMenuModel menuModel) {
        PrimaryMenuDao menuDao = null;
        switch (menuModel.getMenuType()) {
            case TODO:
                menuDao = new PrimaryMenuDao(menuModel.getMenuName(), "TODO", menuModel.isSelected());
                break;
            case ASSIGNMENT:
                menuDao = new PrimaryMenuDao(menuModel.getMenuName(), "ASSIGNMENT", menuModel.isSelected());
                break;
            case MAIL:
                menuDao = new PrimaryMenuDao(menuModel.getMenuName(), "MAIL", menuModel.isSelected());
                break;
            case DRIVE:
                menuDao = new PrimaryMenuDao(menuModel.getMenuName(), "DRIVE", menuModel.isSelected());
                break;
            case WEBLINK:
                menuDao = new PrimaryMenuDao(menuModel.getMenuName(), "WEBLINK", menuModel.isSelected());
                break;
            case TIMETABLE:
                menuDao = new PrimaryMenuDao(menuModel.getMenuName(), "TIMETABLE", menuModel.isSelected());
                break;
            case CALENDAR:
                menuDao = new PrimaryMenuDao(menuModel.getMenuName(), "CALENDAR", menuModel.isSelected());
                break;
        }
        return menuDao;
    }
}
