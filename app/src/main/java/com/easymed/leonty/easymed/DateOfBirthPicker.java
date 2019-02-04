package com.easymed.leonty.easymed;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DateOfBirthPicker {

    private Calendar date = Calendar.getInstance();
    private Context context;
    private TextView dateView;
    private long currentDate = Calendar.getInstance().getTimeInMillis();

    DateOfBirthPicker(Context context, final TextView dateView) {
        this.context = context;
        this.dateView = dateView;

        dateView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    showDialog();
                }
            }
        });
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        new DatePickerDialog(context, listener,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setDate() {
        if (date.getTimeInMillis() <= currentDate) {
            set(date.getTimeInMillis());
        }
        else {
            set(currentDate);
        }
    }

    private void set(long someDate) {
        dateView.setText(DateUtils.formatDateTime(
                context,
                someDate,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR)
        );
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate();
        }
    };
}
