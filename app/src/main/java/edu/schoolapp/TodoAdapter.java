package edu.schoolapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.schoolapp.Model.TodoModel;

public class TodoAdapter extends BaseAdapter {

    private List<TodoModel> todoList;

    TodoAdapter(List<TodoModel> todoList) {
        this.todoList = todoList;
    }

    @Override
    public int getCount() {
        return todoList == null ? 0 : todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
            TodoViewHolder viewHolder = new TodoViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.setupData((TodoModel) getItem(position));
            return convertView;
        }
        ((TodoViewHolder) convertView.getTag()).setupData((TodoModel) getItem(position));
        return convertView;
    }

    public void updateData(List<TodoModel> list) {
        if (todoList == null) {
            todoList = new ArrayList<>();
        } else {
            todoList.clear();
        }
        todoList.addAll(list);
        notifyDataSetChanged();
    }

    static class TodoViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;

        TodoViewHolder(View view) {
            this.titleTextView = view.findViewById(R.id.title_text_view);
            this.descriptionTextView = view.findViewById(R.id.description_text_view);
            this.dateTextView = view.findViewById(R.id.date_text_view);
        }

        void setupData(TodoModel data) {
            //TODO setup data here
            titleTextView.setText(data.getTitle());
            descriptionTextView.setText(data.getDescription());
            dateTextView.setText(data.getDate());
        }
    }
}
