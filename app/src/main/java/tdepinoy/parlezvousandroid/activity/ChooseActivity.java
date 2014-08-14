package tdepinoy.parlezvousandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import tdepinoy.parlezvousandroid.R;

public class ChooseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        String username = getIntent().getStringExtra("username");
        //String password = getIntent().getStringExtra("password");
        Toast.makeText(this, "Utilisateur authentifié: " + username, Toast.LENGTH_LONG).show();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(ChooseActivity.this, MessageActivity.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        intent.putExtra("password", getIntent().getStringExtra("password"));
        startActivity(intent);
    }

    public void showListMessages(View view) {
        Intent intent = new Intent(ChooseActivity.this, ListMessageActivity.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        intent.putExtra("password", getIntent().getStringExtra("password"));
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose, menu);
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
}
