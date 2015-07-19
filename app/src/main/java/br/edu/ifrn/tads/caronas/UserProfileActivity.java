package br.edu.ifrn.tads.caronas;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifrn.tads.caronas.data.User;
import br.edu.ifrn.tads.caronas.data.UserDAO;


public class UserProfileActivity extends ActionBarActivity {
    private User profile;
    UserSaveASyncTask mSaveTask;
    private EditText user_name_text;
    private EditText user_email_text;
    private EditText user_phone_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_actvity);
        user_name_text = (EditText) findViewById(R.id.user_name_text);
        user_email_text = (EditText) findViewById(R.id.user_email_text);
        user_phone_text = (EditText) findViewById(R.id.user_phone_text);
        profile = App.getCurrentUser();
        putOnView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile_actvity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profile_action_save) {
            if(mSaveTask == null) {
                getDataAndValidate();
                mSaveTask = new UserSaveASyncTask();
                mSaveTask.execute();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDataAndValidate() {
        Resources res = getResources();
        final String name = user_name_text.getText().toString();
        if(name.isEmpty()) {
            user_name_text.setError(res.getString(R.string.required_field));
        } else {
            profile.setName(name);
        }
        final String email = user_email_text.getText().toString();
        if(email.isEmpty()){
            user_email_text.setError(res.getString(R.string.required_field));
        } else {
            profile.setEmail(email);
        }
        final String phone = user_phone_text.getText().toString();
        if(phone.isEmpty()) {
            user_phone_text.setError(res.getString(R.string.required_field));
        } else {
            profile.setPhone(phone);
        }
    }

    private void putOnView() {
        user_name_text.setText(profile.getName());
        user_email_text.setText(profile.getEmail());
        user_phone_text.setText(profile.getPhone());
    }

    class UserSaveASyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            UserDAO dao = new UserDAO();
            dao.saveOrUpdate(profile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mSaveTask = null;
            Toast.makeText(UserProfileActivity.this, R.string.user_profile_saved, Toast.LENGTH_SHORT).show();
        }
    }
}
