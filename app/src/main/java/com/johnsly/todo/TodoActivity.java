package com.johnsly.todo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
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
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class TodoActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private EditText meditText;
    private ListView mListView;
    private TaskAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

//assign input variables
        meditText = (EditText) findViewById(R.id.editText);
        mListView = (ListView) findViewById(R.id.listView);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
        }

        myAdapter = new TaskAdapter(this, new ArrayList<Task>());
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(this);

        updateData();

    }


//checking if the text input isnt empty ,then pass datat to parse db
    public void createTask(View v) {
        if (meditText.getText().length() > 0) {
            Task t = new Task();
            t.setACL(new ParseACL(ParseUser.getCurrentUser()));
            t.setUser(ParseUser.getCurrentUser());
            t.setDescription(meditText.getText().toString());
            t.setCompleted(false);
            t.saveEventually();
            meditText.setText("");
            myAdapter.insert(t, 0);
        }
    }


//Querying parse
    public void updateData(){
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        //making tasks belong to the users who created them
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        //adding a local cache to enable faster loading
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
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
        switch (item.getItemId()) {
            case R.id.action_logout:
                ParseUser.logOut();
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }


    //implementing the strikethro
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Task task = myAdapter.getItem(position);
        TextView taskDescription = (TextView) view.findViewById(R.id.task_description);

        task.setCompleted(!task.isCompleted());

        if(task.isCompleted()){
            taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        task.saveEventually();
    }
}
