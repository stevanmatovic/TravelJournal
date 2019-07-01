package com.example.traveljournal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.traveljournal.R;
import com.example.traveljournal.adapter.CheckListAdapter;
import com.example.traveljournal.entity.CheckList;
import com.example.traveljournal.entity.CheckListItem;
import com.example.traveljournal.entity.CheckListWithItems;
import com.example.traveljournal.viewmodel.CheckListViewModel;

public class NewChecklistActivity extends AppCompatActivity {

    private EditText titleET;
    private Long tripId;
    private ListView listView;
    private CheckListAdapter adapter;
    private Long checklistId;
    private CheckListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_checklist);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        Bundle bundle = getIntent().getExtras();
        tripId = bundle.getLong("tripId");
        titleET = findViewById(R.id.title);

        viewModel = ViewModelProviders.of(this).get(CheckListViewModel.class);
        listView = findViewById(R.id.checklist_list_view);
        registerForContextMenu(listView);
        adapter = new CheckListAdapter();
        listView.setAdapter(adapter);


        if (bundle.containsKey("checklistId")) {
            checklistId = bundle.getLong("checklistId");
            viewModel.getCheckListWithItemsForId(checklistId).observe(this, checkListObserver);
        } else {
            new insertNewChecklistTask().execute();
        }

        final LinearLayout newListItem = findViewById(R.id.linearLayout_new_item);
        newListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogBuilder();
            }
        });
    }

    @Override
    protected void onStop() {
        viewModel.updateTitle(checklistId, titleET.getText().toString());
        super.onStop();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        CheckListItem clicked = (CheckListItem) adapter.getItem(info.position);
        menu.add("Delete");
        if (clicked.isChecked()) {
            menu.add("Uncheck");
        } else {
            menu.add("Check");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        CheckListItem clicked = (CheckListItem) adapter.getItem(info.position);
        switch (item.getTitle().toString()) {
            case "Delete":
                viewModel.deleteItem(clicked);
                return true;
            case "Check":
                viewModel.setChecked(clicked.getId(), true);
                return true;
            case "Uncheck":
                viewModel.setChecked(clicked.getId(), false);
                return true;
            default:
                return super.onContextItemSelected(item);


        }
    }

    private void showAlertDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckListItem item = new CheckListItem(input.getText().toString(), false, checklistId);
                viewModel.insert(item);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private class insertNewChecklistTask extends AsyncTask<Void, Void, Void> {

        Long newChecklistId;

        @Override
        protected Void doInBackground(Void... a) {
            newChecklistId = viewModel.insert(new CheckList("Title", tripId));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            checklistId = newChecklistId;
            viewModel.getCheckListWithItemsForId(checklistId).observe(NewChecklistActivity.this, checkListObserver);
        }
    }

    Observer<CheckListWithItems> checkListObserver = new Observer<CheckListWithItems>() {
        @Override
        public void onChanged(CheckListWithItems checkListWithItems) {
            if (checkListWithItems != null) {
                titleET.setText(checkListWithItems.getTitle());
                adapter.setItems(checkListWithItems.getItems());
            }
        }
    };

}
