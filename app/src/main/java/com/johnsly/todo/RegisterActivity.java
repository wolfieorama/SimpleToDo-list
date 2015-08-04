package com.johnsly.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by root on 8/4/15.
 */
public class RegisterActivity extends Activity {

    private EditText musernamefield;
    private EditText mpasswordfield;
    private TextView merrorfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        musernamefield = (EditText) findViewById(R.id.register_username);
        mpasswordfield = (EditText) findViewById(R.id.register_password);
        merrorfield = (TextView) findViewById(R.id.error_messages);
    }

    //registering a user
    public void register(final View v){
        if(musernamefield.getText().length() == 0 || mpasswordfield.getText().length() == 0)
            return;

        v.setEnabled(false);
        ParseUser user = new ParseUser();
        user.setUsername(musernamefield.getText().toString());
        user.setPassword(mpasswordfield.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(RegisterActivity.this, TodoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    switch(e.getCode()){
                        case ParseException.USERNAME_TAKEN:
                            merrorfield.setText("Sorry, this username has already been taken.");
                            break;
                        case ParseException.USERNAME_MISSING:
                            merrorfield.setText("Sorry, you must supply a username to register.");
                            break;
                        case ParseException.PASSWORD_MISSING:
                            merrorfield.setText("Sorry, you must supply a password to register.");
                            break;
                        default:
                            merrorfield.setText(e.getLocalizedMessage());
                    }
                    v.setEnabled(true);
                }
            }
        });

    }

    public void showLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
