package br.edu.ifrn.tads.caronas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifrn.tads.caronas.data.Entity;
import br.edu.ifrn.tads.caronas.data.EntityDAO;
import br.edu.ifrn.tads.caronas.data.RideRequest;
import br.edu.ifrn.tads.caronas.data.RideRequestDAO;
import br.edu.ifrn.tads.caronas.data.Travel;
import br.edu.ifrn.tads.caronas.data.TravelDAO;


public class TravelDetailActivity extends ActionBarActivity implements View.OnClickListener {

    private Travel travel;
    private RideRequest mRideRequest;
    private TextView travel_origin;
    private TextView travel_destination;
    private TextView travel_vacancies;
    private TextView aperture_date;
    private TextView arrival_date;
    private TextView driver;
    private Button askRide;
    private SaveAsyncTask mSaveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);

        travel_origin = (TextView) findViewById(R.id.travel_origin_text);
        travel_destination = (TextView) findViewById(R.id.travel_destination_text);
        travel_vacancies = (TextView) findViewById(R.id.travel_vacancies_text);
        aperture_date = (TextView) findViewById(R.id.travel_aperture_date_text);
        arrival_date = (TextView) findViewById(R.id.travel_arrival_date_text);
        driver = (TextView) findViewById(R.id.travel_driver_text);
        askRide = (Button) findViewById(R.id.travel_ask_ride_button);
        askRide.setOnClickListener(this);

        Intent in = getIntent();
        travel = (Travel) in.getSerializableExtra("travel");
        mRideRequest = (RideRequest) in.getSerializableExtra("request");
        putOnView();
    }

    private void putOnView() {
        travel_origin.setText(travel.getOrigin());
        travel_destination.setText(travel.getDestination());
        aperture_date.setText(travel.getApertureDate().toString());
        arrival_date.setText(travel.getArrivalDate().toString());
        travel_vacancies.setText(String.valueOf(travel.getVacancies()));
        driver.setText(travel.getDriver().getName());
        if(travel.getDriver().equals(App.getCurrentUser())) {
            askRide.setText(R.string.travel_cancel);
        } else if(mRideRequest != null) {
            askRide.setText(R.string.travel_cancel_ride);
        } else {
            askRide.setText(R.string.travel_request_ride);
        }
    }

    @Override
    public void onClick(View v) {
        int btnTextId, toastTextId;
        EntityDAO<?> dao;
        Entity entity;
        if(travel.getDriver().equals(App.getCurrentUser())) {
            // it's user travel
            travel.cancel();

            dao = new TravelDAO();
            entity = travel;
            btnTextId = R.string.travel_cancelling;
            toastTextId = R.string.travel_cancelled;
        } else if(mRideRequest != null) {
            // travel has user request
            mRideRequest.cancel();

            dao = new RideRequestDAO();
            entity = mRideRequest;
            btnTextId = R.string.travel_cancelling;
            toastTextId = R.string.travel_request_cancelled;
        } else {
            // another user travel
            dao = new RideRequestDAO();
            entity = new RideRequest(travel, App.getCurrentUser());
            btnTextId = R.string.travel_request_sending;
            toastTextId = R.string.travel_request_made;
        }

        mSaveTask = new SaveAsyncTask(dao, btnTextId, toastTextId);
        mSaveTask.execute(entity);
    }

    class SaveAsyncTask extends AsyncTask<Entity, Void, Void> {
        private final EntityDAO<?> mDao;
        private final int mBtnText;
        private final int mToastText;

        public SaveAsyncTask(EntityDAO<?> dao, int btnTextResId, int toastResId) {
            mDao = dao;
            mBtnText = btnTextResId;
            mToastText = toastResId;
        }

        @Override
        protected void onPreExecute() {
            askRide.setEnabled(false);
            askRide.setText(mBtnText);
        }

        @Override
        protected Void doInBackground(Entity... params) {
            Entity mEntity = params[0];
            if(mEntity.getRevision() == null || mEntity.getRevision().isEmpty()) {
                mDao.save(mEntity);
            } else {
                mDao.update(mEntity);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(TravelDetailActivity.this, mToastText, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
