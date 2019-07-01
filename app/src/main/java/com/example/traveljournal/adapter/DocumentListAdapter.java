package com.example.traveljournal.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.traveljournal.R;
import com.example.traveljournal.entity.Document;
import com.example.traveljournal.entity.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.DocumentViewHolder> {

    private List<Document> documents;
    private View.OnClickListener onItemClickListener;
    int contextMenuPosition;

    public class DocumentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private String path;
        public TextView title;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.document_card_title);

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            itemView.setOnCreateContextMenuListener(this);
        }

        public String getPath() {
            return path;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add("Delete");
        }
    }

    public DocumentListAdapter() {
        this.documents = new ArrayList<>();
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_list_item,parent,false);
        DocumentViewHolder vh = new DocumentViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentViewHolder holder, int position) {
        holder.title.setText(documents.get(position).getName());
        holder.path = documents.get(position).getPath();

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setContextMenuPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull DocumentViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if(documents == null)
            return 0;
        return documents.size();
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setDocuments(List<Document> documents) {
        this.documents = documents;
        notifyDataSetChanged();
    }

    public int getContextMenuPosition() {
        return contextMenuPosition;
    }

    public void setContextMenuPosition(int contextMenuPosition) {
        this.contextMenuPosition = contextMenuPosition;
    }

    @Override
    public long getItemId(int position) {
        return documents.get(position).getId();
    }
}
