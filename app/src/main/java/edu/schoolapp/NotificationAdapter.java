package edu.schoolapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationModel> notificationList;

    public NotificationAdapter(List<NotificationModel> items) {
        notificationList = items;
    }

    @Override
    public int getItemCount() {
        return notificationList == null ? 0 : notificationList.size();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.setupData(notificationList.get(position));
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView contentTextView;
        private TextView notificationTimeTextView;

        NotificationViewHolder(View itemView) {
            super(itemView);

            contentTextView = itemView.findViewById(R.id.notification_content_text_view);
            notificationTimeTextView = itemView.findViewById(R.id.time_text_view);
        }

        void setupData(NotificationModel item) {
            contentTextView.setText(item.getContent());
            notificationTimeTextView.setText(item.getNotificationTime());
        }
    }
}
