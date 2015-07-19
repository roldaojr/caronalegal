package br.edu.ifrn.tads.caronas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import br.edu.ifrn.tads.caronas.data.Travel;
import br.edu.ifrn.tads.caronas.data.TravelDAO;


public class AddTravelActivity extends AppCompatActivity {

    private String TAG = AddTravelActivity.class.getName();
    private Travel travel;
    private TextView aperture_date_editText;
    private TextView aperture_time_editText;
    private TextView arrival_date_editText;
    private TextView arrival_time_editText;
    private TextView travel_origin_editText;
    private TextView travel_destination_editText;
    private TextView travel_vacancies_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        travel = new Travel();
        travel_origin_editText = (TextView) findViewById(R.id.travel_origin_text);
        travel_destination_editText = (TextView) findViewById(R.id.travel_destination_text);
        travel_vacancies_editText = (TextView) findViewById(R.id.travel_vacancies_text);
        aperture_date_editText = (TextView) findViewById(R.id.travel_aperture_date_text);
        aperture_time_editText = (TextView) findViewById(R.id.travel_aperture_time_editText);
        arrival_date_editText = (TextView) findViewById(R.id.travel_arrival_date_text);
        arrival_time_editText = (TextView) findViewById(R.id.travel_arrival_time_editText);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_travel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_travel_action_save) {
            validate();
            TravelDAO dao = new TravelDAO();
            dao.save(travel);
            Toast.makeText(this, "Viagem cadastrada", Toast.LENGTH_SHORT).show();
        }
        finish();
        return true;
    }

    private void validate() {
        final String origin = travel_origin_editText.getText().toString();
        if(origin.isEmpty()) {
            travel_origin_editText.setError("Campo obrigatório");
        } else {
            travel.setOrigin(origin);
        }
        final String destination = travel_destination_editText.getText().toString();
        if(destination.isEmpty()) {
            travel_destination_editText.setError("Campo obrigatório");
        } else {
            travel.setDestination(destination);
        }
        final String vacancies = travel_vacancies_editText.getText().toString();
        if(vacancies.isEmpty() || !vacancies.matches("[0-9]")){
            travel_vacancies_editText.setError("Valor inválido");
        } else {
            travel.setVacancies(Integer.parseInt(vacancies));
        }
        travel.setDriver(App.getCurrentUser());
    }
}
