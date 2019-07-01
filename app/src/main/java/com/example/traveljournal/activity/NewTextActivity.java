package com.example.traveljournal.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.traveljournal.R;
import com.example.traveljournal.database.DataRepository;
import com.example.traveljournal.entity.Text;

import androidx.appcompat.app.AppCompatActivity;

public class NewTextActivity extends AppCompatActivity{

    private EditText titleET;
    private EditText textET;
    private Long tripId;
    private Text currentText;
    private DataRepository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_text);

        Bundle bundle = getIntent().getExtras();
        tripId = bundle.getLong("tripId");

        repository = new DataRepository(getApplication());

        titleET = findViewById(R.id.title);
        textET = findViewById(R.id.text);

        if(bundle.containsKey("textId")){
            Long textId = bundle.getLong("textId");
            new loadTextTask().execute(textId);
        }


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(textET.getText()) && TextUtils.isEmpty(titleET.getText())){
                    finish();
                }else if(currentText != null){
                    currentText.setText(textET.getText().toString());
                    currentText.setTitle(titleET.getText().toString());
                    repository.update(currentText);
                    finish();
                }else{
                    String title = titleET.getText().toString();
                    String text = textET.getText().toString();
                    repository.insert(new Text(title,text,tripId));

                    finish();
                }
            }
        });
    }

    private class loadTextTask extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... longs) {
            currentText = repository.getTextById(longs[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(currentText!=null){
                titleET.setText(currentText.getTitle());
                textET.setText(currentText.getText());
            }
        }
    }
}
