package br.edu.ifrn.tads.caronas;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.edu.ifrn.tads.caronas.adapters.TravelListAdapter;
import br.edu.ifrn.tads.caronas.data.Travel;
import br.edu.ifrn.tads.caronas.data.TravelDAO;


public class TravelListActivity extends ActionBarActivity implements ListView.OnItemClickListener {
    TravelListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);

        listView = (ListView) findViewById(R.id.travel_list_view);
        listView.setOnItemClickListener(this);
        adapter = new TravelListAdapter(this);
        listView.setAdapter(adapter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    protected void loadData() {
        TravelDAO dao = new TravelDAO();
        adapter.clear();
        adapter.addAll(dao.findAllByUser(App.getCurrentUser()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, TravelDetailActivity.class);
        Travel t = adapter.getItem(position);
        i.putExtra("travel", t);
        i.putExtra("my", true);
        startActivity(i);
    }
}
