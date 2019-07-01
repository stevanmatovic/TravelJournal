package com.example.traveljournal.fragment;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.traveljournal.R;
import com.example.traveljournal.activity.TripActivity;
import com.example.traveljournal.adapter.DocumentListAdapter;
import com.example.traveljournal.entity.Document;
import com.example.traveljournal.utils.FileUtils;
import com.example.traveljournal.viewmodel.DocumentsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class DocumentsFragment extends Fragment {

    private static final int FILE_SELECT_CODE = 101;


    private DocumentsViewModel documentsViewModel;
    private DocumentListAdapter adapter;
    private Long tripId;

    public DocumentsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TripActivity parent = (TripActivity) getActivity();
        tripId = parent.getTripId();

        documentsViewModel = ViewModelProviders.of(this).get(DocumentsViewModel.class);
        adapter = new DocumentListAdapter();
        adapter.setOnItemClickListener(onItemClickListener);
        documentsViewModel.getDocumentsForTrip(tripId).observe(this, new Observer<List<Document>>() {
            @Override
            public void onChanged(List<Document> documents) {
                adapter.setDocuments(documents);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.documents_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);

        FloatingActionButton fab = view.findViewById(R.id.fab_new_document);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
            }
        });

        return view;
    }


    public void showFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        //check for permission
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0
            );

        }else {
            startActivityForResult(Intent.createChooser(intent,"Choose a File"), FILE_SELECT_CODE);
        }

    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            DocumentListAdapter.DocumentViewHolder vh = (DocumentListAdapter.DocumentViewHolder) v.getTag();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(vh.getPath()),"application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = adapter.getContextMenuPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        Long tripId = adapter.getItemId(position);
        documentsViewModel.deleteById(tripId);
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String displayName = null;

                    String pdfPath = FileUtils.getPath(getActivity(),uri);

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }

                    documentsViewModel.insert(new Document(pdfPath,displayName,tripId));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
