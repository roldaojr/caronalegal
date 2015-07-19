package br.edu.ifrn.tads.caronas;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class TimePickerClickListener implements View.OnClickListener {

    TextView targetView;
    DateGetterSetter dateGS;
    Context context;

    public TimePickerClickListener(Context context, TextView targetView, DateGetterSetter dateGS) {
        this.context = context;
        this.targetView = targetView;
        this.dateGS = dateGS;
    }

    @Override
    public void onClick(View v) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateGS.getDate());
        int mHour = cal.get(Calendar.HOUR_OF_DAY);
        int mMinute = cal.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        dateGS.setDate(cal.getTime());
                        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
                        targetView.setText(timeFormat.format(cal.getTime()));
                    }
                },
                mHour, mMinute, false);
        tpd.show();
    }
}
