package com.johnsly.todo;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class TodoActivity extends ActionBarActivity {
    private EditText meditText;
    private ListView mListView;
    private TaskAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        meditText = (EditText) findViewById(R.id.editText);
        mListView = (ListView) findViewById(R.id.listView);

        myAdapter = new TaskAdapter(this, new ArrayList<Task>());
        mListView.setAdapter(myAdapter);

        updateData();

    }



    public void createTask(View v) {
        if (meditText.getText().length() > 0) {
            Task t = new Task();
            t.setDescription(meditText.getText().toString());
            t.setCompleted(false);
            t.saveEventually();
            meditText.setText("");
            myAdapter.insert(t, 0);
        }
    }



    public void updateData(){
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        query.findInBackground(new FindCallback<Task>() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void done(List<Task> list, com.parse.ParseException e) {
                if(list != null){
                    myAdapter.clear();
                    myAdapter.addAll(list);
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

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
