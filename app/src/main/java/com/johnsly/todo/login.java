package com.johnsly.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login extends AppCompatActivity {

    private EditText musernamefield;
    private EditText mpasswordfield;
    private TextView merrorfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        musernamefield = (EditText) findViewById(R.id.login_username);
        mpasswordfield = (EditText) findViewById(R.id.login_password);
        merrorfield = (TextView) findViewById(R.id.error_messages);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public void signIn(final View v) {
        v.setEnabled(false);
        ParseUser.logInInBackground(musernamefield.getText().toString(), mpasswordfield.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent = new Intent(login.this, TodoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    switch (e.getCode()) {
                        case ParseException.USERNAME_TAKEN:
                            merrorfield.setText("Sorry, this username has already been taken.");
                            break;
                        case ParseException.USERNAME_MISSING:
                            merrorfield.setText("Sorry, you must supply a username to register.");
                            break;
                        case ParseException.PASSWORD_MISSING:
                            merrorfield.setText("Sorry, you must supply a password to register.");
                            break;
                        case ParseException.OBJECT_NOT_FOUND:
                            merrorfield.setText("Sorry, those credentials were invalid.");
                            break;
                        default:
                            merrorfield.setText(e.getLocalizedMessage());
                            break;
                    }
                    v.setEnabled(true);
                }
            }
        });
    }

    public void showRegistration(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
