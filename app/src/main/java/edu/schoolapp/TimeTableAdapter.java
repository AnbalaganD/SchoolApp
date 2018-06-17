package edu.schoolapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter {

//    List<>

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class TimeTableViewHolder extends RecyclerView.ViewHolder {

        public TimeTableViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class TimeTableHeaderViewHolder extends RecyclerView.ViewHolder {

        public TimeTableHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
