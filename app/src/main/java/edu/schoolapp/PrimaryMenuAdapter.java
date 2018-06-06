package edu.schoolapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PrimaryMenuAdapter extends RecyclerView.Adapter<PrimaryMenuAdapter.PrimaryMenuViewHolder> {

    private List<PrimaryMenuModel> primaryMenuList;
    private PrimaryMenuSelectListener listener;

    public PrimaryMenuAdapter(List<PrimaryMenuModel> primaryMenuList, PrimaryMenuSelectListener listener) {
        this.primaryMenuList = primaryMenuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PrimaryMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_primary_menu, parent, false);
        return new PrimaryMenuViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PrimaryMenuViewHolder holder, int position) {
        holder.setupData(primaryMenuList.get(position));
    }

    @Override
    public int getItemCount() {
        return primaryMenuList == null ? 0 : primaryMenuList.size();
    }

    public void updateData(List<PrimaryMenuModel> list) {
        if (this.primaryMenuList == null) {
            this.primaryMenuList = new ArrayList<>();
        } else {
            this.primaryMenuList.clear();
        }
        this.primaryMenuList.addAll(list);
        notifyDataSetChanged();
    }

    class PrimaryMenuViewHolder extends RecyclerView.ViewHolder {

        private PrimaryMenuSelectListener listener;
        private PrimaryMenuModel data;

        private ImageView menuImage;
        private TextView menuTitle;

        PrimaryMenuViewHolder(View itemView, final PrimaryMenuSelectListener listener) {
            super(itemView);
            this.listener = listener;

            menuImage = itemView.findViewById(R.id.menu_image_view);
            menuTitle = itemView.findViewById(R.id.menu_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMenuSelect(data);
                }
            });
        }

        void setupData(PrimaryMenuModel data) {
            this.data = data;

            menuImage.setImageResource(data.getMenuImageResource());
            menuTitle.setText(data.getMenuName());
        }
    }
}
