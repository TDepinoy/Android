package tdepinoy.parlezvousandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import tdepinoy.parlezvousandroid.R;


public class LoginActivity extends Activity {

    private static final String HOST = "http://parlezvous.herokuapp.com/";

    private ProgressBar progressBar;
    private EditText password;
    private EditText username;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
        error = (TextView) findViewById(R.id.error_message);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void send(View view) {

        if (TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())) {
            error.setText(getString(R.string.error_empty_fields));
            error.setVisibility(View.VISIBLE);
        } else {
            new LoginTask().execute(username.getText().toString(), password.getText().toString());
        }
    }

    public void cleanForm(View view) {
        username.setText("");
        password.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LoginTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            error.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(HOST + "connect/" + username + "/" + password)
                    .build();


            try {
                Response response = client.newCall(request).execute();
                boolean loginValid = Boolean.valueOf(response.body().string());
                if (loginValid)
                    return true;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressBar.setVisibility(View.INVISIBLE);

            if (result) {
                Intent intent = new Intent(LoginActivity.this, ChooseActivity.class);
                intent.putExtra("username", username.getText().toString());
                intent.putExtra("password", password.getText().toString());
                startActivity(intent);
            } else {
                error.setText(getString(R.string.error_login_invalid));
                error.setVisibility(View.VISIBLE);
            }
        }
    }
}
