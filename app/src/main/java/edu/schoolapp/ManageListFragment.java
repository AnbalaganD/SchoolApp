package edu.schoolapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ManageListFragment extends AppCompatActivity {

    private ManageListAdapter manageListAdapter;
    private List<ManageListModel> manageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_manage_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView manageListView = findViewById(R.id.manage_list_view);

        manageList = new ArrayList<>();
        addManageList();
        manageListAdapter = new ManageListAdapter(manageList);
        manageListView.setAdapter(manageListAdapter);

        findViewById(R.id.manage_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO update your manage list here
            }
        });

        manageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ManageListModel manageListModel = manageList.get(position);
                manageListModel.setSelected(!manageListModel.isSelected());
                manageListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addManageList() {
        manageList.add(new ManageListModel(R.drawable.todo, "Todo"));
        manageList.add(new ManageListModel(R.drawable.assignment, "Assignment"));
        manageList.add(new ManageListModel(R.drawable.web_link, "Web Link"));
        manageList.add(new ManageListModel(R.drawable.mail, "Mail"));
        manageList.add(new ManageListModel(R.drawable.drive, "Drive"));
        manageList.add(new ManageListModel(R.drawable.schedule, "TimeTable"));
        manageList.add(new ManageListModel(R.drawable.time_table, "Calendar"));
    }
}
