package edu.schoolapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.schoolapp.Model.AssignmentModel;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentVH> {

    private List<AssignmentModel> assignmentModelList;

    public AssignmentAdapter(List<AssignmentModel> list) {
        assignmentModelList = list;
    }

    @NonNull
    @Override
    public AssignmentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        return new AssignmentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentVH holder, int position) {
        holder.setupData(assignmentModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return assignmentModelList == null ? 0 : assignmentModelList.size();
    }

    public void updateData(List<AssignmentModel> list) {
        if (assignmentModelList == null) {
            assignmentModelList = new ArrayList<>();
        }
        assignmentModelList.clear();
        assignmentModelList.addAll(list);
        notifyDataSetChanged();
    }

    static class AssignmentVH extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView unitTextView;
        private TextView descriptionTextView;
        private TextView markTextView;

        AssignmentVH(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_text_view);
            unitTextView = itemView.findViewById(R.id.subject_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            markTextView = itemView.findViewById(R.id.mark_text_view);
        }

        void setupData(AssignmentModel item) {
            titleTextView.setText(item.getTitle());
            unitTextView.setText(item.getUnit());
            descriptionTextView.setText(item.getDescription());
            markTextView.setText(String.format("%d out of %d", item.getMarkObtained(), item.getMark()));
        }
    }
}
