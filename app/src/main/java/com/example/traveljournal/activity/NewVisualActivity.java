package com.example.traveljournal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.traveljournal.R;
import com.example.traveljournal.database.DataRepository;
import com.example.traveljournal.entity.Visual;

public class NewVisualActivity extends AppCompatActivity {

    public static final int REQUEST_LOAD_IMAGE = 1;

    private EditText titleET;
    private ImageView imageView;
    private boolean imageLoaded;
    private String imagePath;
    private Long tripId;
    private Visual currentVisual;
    private DataRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visual);

        Bundle bundle = getIntent().getExtras();
        tripId = bundle.getLong("tripId");

        titleET = findViewById(R.id.title);
        imageView = findViewById(R.id.imageView);
        imageLoaded= false;

        if(bundle.containsKey("visualId")){
            Long visualId = bundle.getLong("visualId");
            new loadVisualTask().execute(visualId);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageLoaded) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(NewVisualActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0
                                );

                    }else {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, REQUEST_LOAD_IMAGE);
                    }
                }
            }
        });

        repository = new DataRepository(getApplication());

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (currentVisual != null){
                    currentVisual.setTitle(titleET.getText().toString());
                    repository.update(currentVisual);
                }
                else if (imageLoaded){
                    repository.insert(new Visual(titleET.getText().toString(), imagePath,tripId));
                }
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();

            // String imagePath contains the imagePath of selected Image
            imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            imageLoaded = true;
        }
    }

    private class loadVisualTask extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... longs) {
            currentVisual = repository.getVisualById(longs[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(currentVisual!=null){
                imageLoaded = true;
                titleET.setText(currentVisual.getTitle());
                imageView.setImageBitmap(BitmapFactory.decodeFile(currentVisual.getPath()));
            }
        }
    }
}
