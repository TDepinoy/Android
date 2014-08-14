package tdepinoy.parlezvousandroid.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import tdepinoy.parlezvousandroid.R;

public class MessageActivity extends Activity {

    private static final String HOST = "http://parlezvous.herokuapp.com/";

    private ProgressBar progressBar;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        message = (EditText) findViewById(R.id.messageTextField);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void sendMessage(View view) {
        if (TextUtils.isEmpty(message.getText().toString().trim())) {
            Toast.makeText(MessageActivity.this, "Le message ne doit pas être vide", Toast.LENGTH_SHORT).show();
        } else {
            new SendMessageTask().execute(getIntent().getStringExtra("username"), getIntent().getStringExtra("password"), message.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message, menu);
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


    private class SendMessageTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            String message = strings[2].replace(" ", "%20");

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(HOST + "message/" + username + "/" + password + "/" + message)
                    .build();

            try {
                client.newCall(request).execute();
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
                Toast.makeText(MessageActivity.this, "Le message a bien été envoyé", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MessageActivity.this, "Le message n'a pas pu être envoyé", Toast.LENGTH_LONG).show();
            }
        }
    }
}
