package br.edu.ifrn.tads.caronas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class DatePickerClickListener implements View.OnClickListener {

    TextView targetView;
    DateGetterSetter dateGS;
    Context context;

    public DatePickerClickListener(Context context, TextView targetView, DateGetterSetter dateGS) {
        this.context = context;
        this.targetView = targetView;
        this.dateGS = dateGS;
    }

    @Override
    public void onClick(View v) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateGS.getDate());
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(year, monthOfYear, dayOfMonth);
                        dateGS.setDate(cal.getTime());
                        DateFormat dateFormat = DateFormat.getDateInstance();
                        targetView.setText(dateFormat.format(cal.getTime()));
                    }
                },
                mYear, mMonth, mDay);
        dpd.show();
    }
}
