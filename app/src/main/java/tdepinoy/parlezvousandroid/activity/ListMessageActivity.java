package tdepinoy.parlezvousandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tdepinoy.parlezvousandroid.R;
import tdepinoy.parlezvousandroid.adapter.MessageAdapter;
import tdepinoy.parlezvousandroid.model.Message;

public class ListMessageActivity extends Activity {

    private static final String HOST = "http://parlezvous.herokuapp.com/";
    private ListView listView;
private MessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        adapter = new MessageAdapter(this);

        listView = (ListView) findViewById(R.id.listViewId);
        listView.setAdapter(adapter);

        new LoadMessagesTask().execute(getIntent().getStringExtra("username"), getIntent().getStringExtra("password"));

    }

    public void refreshMessages (List<Message> messages) {
        adapter.updateMessages(messages);
    }

    public void refresh(View view) {
        new LoadMessagesTask().execute(getIntent().getStringExtra("username"), getIntent().getStringExtra("password"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_message, menu);
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

    private class LoadMessagesTask extends AsyncTask<String, String, List<Message>> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected  List<Message> doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(HOST + "messages/" + username + "/" + password)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();

                String[] splitResult = result.split(";");

                List<Message> messages = new ArrayList<Message>();

                for(String s : splitResult) {
                    String[] messageString = s.split(":");
                    Message m = new Message();
                    m.setAuthor(messageString[0]);
                    m.setContent(messageString.length > 1  ? messageString[1] : "");
                    messages.add(m);
                }

                return messages;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return Collections.EMPTY_LIST;
        }

        @Override
        protected void onPostExecute( List<Message> messages) {
           refreshMessages(messages);
        }
    }
}
