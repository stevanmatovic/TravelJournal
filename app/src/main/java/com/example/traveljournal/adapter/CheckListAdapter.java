package com.example.traveljournal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traveljournal.R;
import com.example.traveljournal.entity.CheckListItem;

import java.util.ArrayList;
import java.util.List;

public class CheckListAdapter extends BaseAdapter {

    private class CheckListItemViewHolder{

        private final CheckBox checkBox;
        private final TextView textView;

        private CheckListItemViewHolder(View itemView) {
            checkBox = itemView.findViewById(R.id.checkbox);
            textView = itemView.findViewById(R.id.checkbox_item_textview);
        }

    }

    private List<CheckListItem> items;



    public CheckListAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if(items == null)
            return 0;
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CheckListItemViewHolder checkListItemViewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent,false);
            checkListItemViewHolder = new CheckListItemViewHolder(convertView);
            convertView.setTag(checkListItemViewHolder);
        }else{
            checkListItemViewHolder = (CheckListItemViewHolder)convertView.getTag();
        }

        CheckListItem item = items.get(position);
        if(item != null){
            checkListItemViewHolder.textView.setText(item.getName());
            checkListItemViewHolder.checkBox.setChecked(item.isChecked());
        }
        return convertView;
    }


    public List<CheckListItem> getItems() {
        return items;
    }

    public void setItems(List<CheckListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
