package com.example.traveljournal.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traveljournal.R;
import com.example.traveljournal.entity.CheckListItem;
import com.example.traveljournal.entity.CheckListWithItems;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.entity.Text;
import com.example.traveljournal.entity.Visual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComponentGridAdapter extends BaseAdapter {

    private List<Component> componentList;

    public ComponentGridAdapter(List<Component> componentList) {
        this.componentList = componentList;
    }

    public void setComponentList(List<Component> componentList) {
        this.componentList.clear();
        this.componentList.addAll(componentList);
        Collections.sort(this.componentList,new Comparator<Component>() {
            @Override
            public int compare(Component o1, Component o2) {
                if(o1.getCreationDate().before(o2.getCreationDate()))
                    return -1;
                else if(o2.getCreationDate().before(o1.getCreationDate()))
                    return 1;
                return 0;
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(componentList!=null){
            return componentList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return componentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return componentList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        switch (type){
            case Component.TEXT:
                TextViewHolder textViewHolder;

                if(convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_plan_component, parent,false);
                    textViewHolder = new TextViewHolder(convertView);
                    convertView.setTag(textViewHolder);
                }else{
                    textViewHolder = (TextViewHolder)convertView.getTag();
                }

                Text textItem = (Text)componentList.get(position);
                if(textItem != null){
                    if(textViewHolder.content != null)
                        textViewHolder.content.setText(textItem.getText());
                    if(textViewHolder.title != null)
                        textViewHolder.title.setText(textItem.getTitle());
                }
                return convertView;
            case Component.VISUAL:
                VisualViewHolder visualViewHolder;

                if(convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.visual_plan_component, parent,false);
                    visualViewHolder = new VisualViewHolder(convertView);
                    convertView.setTag(visualViewHolder);
                }else{
                    visualViewHolder = (VisualViewHolder) convertView.getTag();
                }

                Visual visualComponent = (Visual)componentList.get(position);
                if(visualComponent != null){

                    visualViewHolder.title.setText(visualComponent.getTitle());
                    visualViewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile(visualComponent.getPath()));
                }
                return convertView;
            case Component.CHECKLIST:
                CheckListViewHolder checkListViewHolder;

                if(convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_plan_component, parent,false);
                    checkListViewHolder = new CheckListViewHolder(convertView);
                    convertView.setTag(checkListViewHolder);
                }else{
                    checkListViewHolder = (CheckListViewHolder) convertView.getTag();
                }
                CheckListWithItems checkListComponent = (CheckListWithItems) componentList.get(position);
                if(checkListComponent != null){

                    checkListViewHolder.title.setText(checkListComponent.getTitle());
                    List<CheckListItem> items = checkListComponent.getItems();
                    List<CheckBox> checkBoxes = checkListViewHolder.checkBoxes;
                    int i = 0;
                    for(i = 0; i<items.size() && i < 3;i++){
                        checkBoxes.get(i).setText(items.get(i).getName());
                        checkBoxes.get(i).setChecked(items.get(i).isChecked());
                        checkBoxes.get(i).setVisibility(View.VISIBLE);
                    }
                    //hide checkboxes if empty
                    while(i<3){
                        checkBoxes.get(i).setVisibility(View.GONE);
                        i++;
                    }
                    if(items.size()<4){
                        checkListViewHolder.expand.setVisibility(View.GONE);
                    }
                }

                return convertView;

        }
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return componentList.get(position).getType();
    }



    class TextViewHolder{

        private TextView title;
        private TextView content;

        public TextViewHolder(View itemView) {
            title = itemView.findViewById(R.id.text_card_title);
            content = itemView.findViewById(R.id.text_card_content);
        }
    }

    class VisualViewHolder{

        private final TextView title;
        private final ImageView imageView;

        private VisualViewHolder(View itemView) {
            title = itemView.findViewById(R.id.visual_card_title);
            imageView = itemView.findViewById(R.id.visual_card_image);
        }

    }

    class CheckListViewHolder{

        private final TextView title;
        private final List<CheckBox> checkBoxes;
        private final ImageView expand;

        private CheckListViewHolder(View itemView) {
            title = itemView.findViewById(R.id.checklist_card_title);
            expand = itemView.findViewById(R.id.expand_drawable);
            checkBoxes= new ArrayList<>();
            checkBoxes.add((CheckBox) itemView.findViewById(R.id.checkbox_0));
            checkBoxes.add((CheckBox) itemView.findViewById(R.id.checkbox_1));
            checkBoxes.add((CheckBox) itemView.findViewById(R.id.checkbox_2));
        }

    }

}
