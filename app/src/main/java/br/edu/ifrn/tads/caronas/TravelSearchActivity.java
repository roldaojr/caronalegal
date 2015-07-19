package br.edu.ifrn.tads.caronas;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import br.edu.ifrn.tads.caronas.data.Travel;

public class TravelSearchActivity extends ActionBarActivity implements View.OnClickListener {
    private Travel travel;
    private TextView travel_origin_editText;
    private TextView travel_destination_editText;
    private TextView aperture_date_editText;
    private TextView aperture_time_editText;
    private TextView arrival_date_editText;
    private TextView arrival_time_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_search);
        travel = new Travel();

        travel_origin_editText = (TextView) findViewById(R.id.travel_origin_text);
        travel_destination_editText = (TextView) findViewById(R.id.travel_destination_text);
        aperture_date_editText = (TextView) findViewById(R.id.travel_aperture_date_text);
        aperture_time_editText = (TextView) findViewById(R.id.travel_aperture_time_editText);
        arrival_date_editText = (TextView) findViewById(R.id.travel_arrival_date_text);
        arrival_time_editText = (TextView) findViewById(R.id.travel_arrival_time_editText);
        Button travel_search_button = (Button) findViewById(R.id.travel_search_button);

        DateGetterSetter apertureDateGetSet = new DateGetterSetter() {
            @Override
            public void setDate(Date date) {
                travel.setApertureDate(date);
            }

            @Override
            public Date getDate() {
                return travel.getApertureDate();
            }
        };

        aperture_date_editText.setOnClickListener(
                new DatePickerClickListener(this, aperture_date_editText, apertureDateGetSet)
        );
        aperture_time_editText.setOnClickListener(
                new TimePickerClickListener(this, aperture_time_editText, apertureDateGetSet)
        );

        DateGetterSetter arrivalDateGetSet = new DateGetterSetter() {
            @Override
            public void setDate(Date date) {
                travel.setArrivalDate(date);
            }

            @Override
            public Date getDate() {
                return travel.getArrivalDate();
            }
        };

        arrival_date_editText.setOnClickListener(
                new DatePickerClickListener(this, arrival_date_editText, arrivalDateGetSet)
        );
        arrival_time_editText.setOnClickListener(
                new TimePickerClickListener(this, arrival_time_editText, arrivalDateGetSet)
        );
        travel_search_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String origin = travel_origin_editText.getText().toString();
        String destination = travel_destination_editText.getText().toString();
        Intent in = new Intent(this, TravelSearchResultsActivity.class);
        in.putExtra("origin", origin);
        in.putExtra("destination", destination);
        startActivity(in);
    }

}
