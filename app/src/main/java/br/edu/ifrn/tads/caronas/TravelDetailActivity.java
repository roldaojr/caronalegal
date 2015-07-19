package br.edu.ifrn.tads.caronas;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import br.edu.ifrn.tads.caronas.data.RideRequest;
import br.edu.ifrn.tads.caronas.data.RideRequestDAO;
import br.edu.ifrn.tads.caronas.data.Travel;
import br.edu.ifrn.tads.caronas.data.TravelDAO;


public class TravelDetailActivity extends ActionBarActivity {

    private Travel travel;
    private TextView travel_origin;
    private TextView travel_destination;
    private TextView travel_vacancies;
    private TextView aperture_date;
    private TextView arrival_date;
    private TextView driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent i = getIntent();
        i.getAction();
        travel = (Travel) i.getSerializableExtra("travel");
        boolean isRequest = i.getBooleanExtra("my", false);
        Log.d(App.TAG, new Gson().toJson(travel));

        travel_origin = (TextView) findViewById(R.id.travel_origin_text);
        travel_destination = (TextView) findViewById(R.id.travel_destination_text);
        travel_vacancies = (TextView) findViewById(R.id.travel_vacancies_text);
        aperture_date = (TextView) findViewById(R.id.travel_aperture_date_text);
        arrival_date = (TextView) findViewById(R.id.travel_arrival_date_text);
        driver = (TextView) findViewById(R.id.travel_driver_text);
        Button askRide = (Button) findViewById(R.id.travel_ask_ride_button);

        if(travel.getDriver().equals(App.getCurrentUser())) {
            askRide.setText(R.string.travel_cancel);
            askRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    travel.cancel();
                    new TravelDAO().update(travel);
                    Toast.makeText(TravelDetailActivity.this, R.string.travel_cancelled, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else if(isRequest) {
            askRide.setText(R.string.travel_cancel_ride);
            askRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RideRequest req;
                    RideRequestDAO dao = new RideRequestDAO();
                    req = dao.getByTravelAndUser(travel, App.getCurrentUser());
                    req.cancel();
                    dao.update(req);
                    Toast.makeText(TravelDetailActivity.this, R.string.travel_request_cancelled, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            askRide.setText(R.string.travel_request_ride);
            askRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RideRequest req = new RideRequest();
                    req.setTravel(travel);
                    req.setUser(App.getCurrentUser());
                    RideRequestDAO dao = new RideRequestDAO();
                    dao.save(req);
                    Toast.makeText(TravelDetailActivity.this, R.string.travel_request_made, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

        fillFromObject(travel);
    }

    private void fillFromObject(Travel t) {
        travel_origin.setText(t.getOrigin());
        travel_destination.setText(t.getDestination());
        aperture_date.setText(t.getApertureDate().toString());
        arrival_date.setText(t.getArrivalDate().toString());
        travel_vacancies.setText(String.valueOf(t.getVacancies()));
        driver.setText(t.getDriver().getName());
    }

}
