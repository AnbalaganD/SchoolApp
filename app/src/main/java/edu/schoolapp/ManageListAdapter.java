package edu.schoolapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ManageListAdapter extends BaseAdapter {

    private List<PrimaryMenuModel> manageList;

    public ManageListAdapter(List<PrimaryMenuModel> manageList) {
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
            viewHolder.setupData((PrimaryMenuModel) getItem(position));
            return convertView;
        }
        ((ManageListViewHolder) convertView.getTag()).setupData((PrimaryMenuModel) getItem(position));
        return convertView;
    }

    public void updateData(List<PrimaryMenuModel> list) {
        if (manageList == null) {
            manageList = new ArrayList<>();
        } else {
            manageList.clear();
        }
        manageList.addAll(list);
        notifyDataSetChanged();
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

        void setupData(PrimaryMenuModel data) {
            manageListImageView.setImageResource(data.getMenuImageResource());
            manageListTitle.setText(data.getMenuName());
            addOrRemoveImageView.setImageResource(data.isSelected() ? R.drawable.minus : R.drawable.plus);
        }
    }
}
