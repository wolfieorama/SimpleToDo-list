package com.johnsly.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 8/2/15.
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    private Context myContext;
    private List<Task> myTask;


    public TaskAdapter(Context context, List<Task> objects) {
        super(context, R.layout.task_row_item, objects);
        this.myContext = context;
        this.myTask = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater mLayoutInflater = LayoutInflater.from(myContext);
            convertView = mLayoutInflater.inflate(R.layout.task_row_item, null);
        }

        Task task = myTask.get(position);

        TextView descriptionView = (TextView) convertView.findViewById(R.id.task_description);

        descriptionView.setText(task.getDescription());

        if(task.isCompleted()){
            descriptionView.setPaintFlags(descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            descriptionView.setPaintFlags(descriptionView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return convertView;
    }

}
