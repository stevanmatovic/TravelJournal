package com.example.traveljournal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.traveljournal.R;
import com.example.traveljournal.database.DataRepository;
import com.example.traveljournal.entity.Trip;
import com.example.traveljournal.utils.EditTextDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewTripActivity extends AppCompatActivity {


    private EditTextDatePicker startDatePicker;
    private EditTextDatePicker endDatePicker;
    private EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        startDatePicker = new EditTextDatePicker(this,R.id.date_start);
        endDatePicker = new EditTextDatePicker(this,R.id.date_end);
        title = findViewById(R.id.trip_title);

        final DataRepository repository = new DataRepository(getApplication());

        final Button buttonSave = findViewById(R.id.button_save_trip);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(TextUtils.isEmpty(startDatePicker.getText()) || TextUtils.isEmpty(endDatePicker.getText())){
                    Toast.makeText(NewTripActivity.this,"Please choose the dates of your travel",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(title.getText())){
                    Toast.makeText(NewTripActivity.this,"Please write the title of your travel",Toast.LENGTH_LONG).show();
                    return;
                }
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = parseDate(startDatePicker.getText(),"dd.MM.yyyy");
                    endDate = parseDate(endDatePicker.getText(),"dd.MM.yyyy");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Trip newTrip = new Trip(title.getText().toString(),startDate,endDate);
                repository.insert(newTrip);
                finish();
            }
        });
    }

    private Date parseDate(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }

}
