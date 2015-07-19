package br.edu.ifrn.tads.caronas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

import br.edu.ifrn.tads.caronas.data.Travel;
import br.edu.ifrn.tads.caronas.data.TravelDAO;

public class TravelSearchResultsActivity extends TravelListActivity {
    protected TravelSearchAsyncTask mAsyncTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void getTravelList() {
        Intent in = getIntent();
        String origin = in.getStringExtra("origin");
        String destination = in.getStringExtra("destination");
        mAsyncTask = new TravelSearchAsyncTask(origin, destination);
        mAsyncTask.execute((Void) null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    class TravelSearchAsyncTask extends TravelListAsyncTask {
        private String mOrigin;
        private String mDestination;

        public TravelSearchAsyncTask(String origin, String destination) {
            mOrigin = origin;
            mDestination = destination;
        }

        @Override
        protected List<Travel> doInBackground(Void... params) {
            TravelDAO dao = new TravelDAO();
            return dao.findByOriginAndDestination(mOrigin, mDestination);
        }
    }
}
