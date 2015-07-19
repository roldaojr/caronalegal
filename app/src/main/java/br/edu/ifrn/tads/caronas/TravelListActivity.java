package br.edu.ifrn.tads.caronas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.edu.ifrn.tads.caronas.adapters.TravelListAdapter;
import br.edu.ifrn.tads.caronas.data.RideRequest;
import br.edu.ifrn.tads.caronas.data.RideRequestDAO;
import br.edu.ifrn.tads.caronas.data.Travel;
import br.edu.ifrn.tads.caronas.data.TravelDAO;
import br.edu.ifrn.tads.caronas.data.User;
import br.edu.ifrn.tads.caronas.data.UserDAO;


public class TravelListActivity extends ActionBarActivity implements ListView.OnItemClickListener {
    private UserSessionManager session;
    protected TravelListAsyncTask mAsyncTask;
    protected TravelListAdapter adapter;
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());
        if(session.requireLogin()) finish();

        listView = (ListView) findViewById(R.id.travel_list_view);
        listView.setOnItemClickListener(this);
        adapter = new TravelListAdapter(this);
        listView.setAdapter(adapter);
    }

    protected void getTravelList() {
        mAsyncTask = new TravelListAsyncTask();
        mAsyncTask.execute((Void) null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getTravelList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_travel_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent i;
        switch (item.getItemId()) {
            case R.id.travel_list_action_add:
                i = new Intent(this, AddTravelActivity.class);
                startActivity(i);
                return true;
            case R.id.travel_list_action_search:
                i = new Intent(this, TravelSearchActivity.class);
                startActivity(i);
                return true;
            case R.id.travel_list_user_profile:
                i = new Intent(this, UserProfileActivity.class);
                startActivity(i);
                return true;
            case R.id.travel_list_user_logout:
                session.logoutUser();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Travel t = adapter.getItem(position);
        FetchDetailTask task = new FetchDetailTask(t);
        task.execute((Void) null);
    }

    protected class TravelListAsyncTask extends AsyncTask<Void, Void, List<Travel>> {
        @Override
        protected List<Travel> doInBackground(Void... params) {
            TravelDAO dao = new TravelDAO();
            return dao.findAllByUser(App.getCurrentUser());
        }

        @Override
        protected void onPostExecute(List<Travel> travels) {
            mAsyncTask = null;
            adapter.clear();
            adapter.addAll(travels);
        }
    }

    class FetchDetailTask extends AsyncTask<Void, Void, RideRequest> {
        private Travel mTravel;

        public FetchDetailTask(Travel mTravel) {
            this.mTravel = mTravel;
        }

        @Override
        protected RideRequest doInBackground(Void... params) {
            User driver = new UserDAO().get(mTravel.getDriver().getId());
            mTravel.setDriver(driver);
            RideRequestDAO dao = new RideRequestDAO();
            return dao.getByTravelAndUser(mTravel, App.getCurrentUser());
        }

        @Override
        protected void onPostExecute(RideRequest rideRequest) {
            Intent in = new Intent(TravelListActivity.this, TravelDetailActivity.class);
            in.putExtra("travel", mTravel);
            in.putExtra("request", rideRequest);
            startActivity(in);
        }
    }
}
