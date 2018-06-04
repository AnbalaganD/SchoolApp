package edu.schoolapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ManageListAdapter extends BaseAdapter {

    private List<ManageListModel> manageList;

    public ManageListAdapter(List<ManageListModel> manageList) {
        this.manageList = manageList;
    }

    @Override
    public int getCount() {
        return manageList == null ? 0 : manageList.size();
    }

    @Override
    public Object getItem(int position) {
        return manageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_list, parent, false);
            ManageListViewHolder viewHolder = new ManageListViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.setupData((ManageListModel) getItem(position)); //TODO Sent data to view holder
            return convertView;
        }
        ((ManageListViewHolder) convertView.getTag()).setupData((ManageListModel) getItem(position));
        return convertView;
    }

    static class ManageListViewHolder {
        ImageView manageListImageView;
        TextView manageListTitle;
        ImageView addOrRemoveImageView;

        ManageListViewHolder(View view) {
            this.manageListImageView = view.findViewById(R.id.list_image_view);
            this.manageListTitle = view.findViewById(R.id.list_title_text_view);
            this.addOrRemoveImageView = view.findViewById(R.id.add_or_remove_image_view);
        }

        void setupData(ManageListModel data) {
            //TODO setup data here
            manageListImageView.setImageResource(data.getManageListImage());
            manageListTitle.setText(data.getTitle());
            addOrRemoveImageView.setImageResource(data.isSelected() ? R.drawable.minus : R.drawable.plus);
        }
    }
}
