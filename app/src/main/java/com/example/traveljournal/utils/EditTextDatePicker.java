package com.example.traveljournal.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;

public class EditTextDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText editText;
    private int day;
    private int month;
    private int year;
    private Context context;

    public EditTextDatePicker(Context context, int editTextViewID)
    {
        Activity act = (Activity)context;
        this.editText = (EditText)act.findViewById(editTextViewID);
        this.editText.setOnClickListener(this);
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        updateDisplay();
    }
    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    // updates the date in the birth date EditText
    private void updateDisplay() {

        editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(day).append(".").append(month + 1).append(".").append(year).append(" "));
    }

    public String getText() {
        return editText.getText().toString();
    }
}
