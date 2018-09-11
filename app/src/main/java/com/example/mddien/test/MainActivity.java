package com.example.mddien.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mddien.test.frequency.ActivityFrequency;
import com.example.mddien.test.swiperotate.RotatableActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CircularItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        // simple text item with numbers 0 ~ 9
        ArrayList<String> itemTitles = new ArrayList<>();
        for(int i = 0 ; i < 6 ; i ++){
            itemTitles.add(String.valueOf(i));
        }


        // usage sample
        final CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);
        adapter = new CircularItemAdapter(getLayoutInflater(), itemTitles);
        circularListView.setAdapter(adapter);
        circularListView.setRadius(100);
        circularListView.setOnItemClickListener(new CircularTouchListener.CircularItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {
                Toast.makeText(MainActivity.this,
                        "view at index " + index + " is clicked!",
                        Toast.LENGTH_SHORT).show();
            }
        });


        // remove item example
        Button btRemoveItem = (Button) findViewById(R.id.bt_remove_item);
        btRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeItemAt(0);
            }
        });


        // add item example
        Button btAddItem = (Button) findViewById(R.id.bt_add_item);
        btAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.view_circular_item, null);
                TextView itemView = (TextView) view.findViewById(R.id.bt_item);
                itemView.setText(String.valueOf(adapter.getCount() + 1));
                adapter.addItem(view);
            }
        });


        // remove item example
        Button btEnlargeRadius = (Button) findViewById(R.id.bt_enlarge_radius);
        btEnlargeRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularListView.setRadius(circularListView.radius += 15);
            }
        });


        // remove item example
        Button btReduceRadius = (Button) findViewById(R.id.bt_reduce_radius);
        btReduceRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularListView.setRadius(circularListView.radius -= 15);
            }
        });

        // start Activity rotate view
        Button btRotateActivity = findViewById(R.id.bt_rotateActivity);
        btRotateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RotatableActivity.class));
            }
        });

        // start Activity frequency
        Button btFrequencyActivity = findViewById(R.id.bt_frequencyActivity);
        btFrequencyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityFrequency.class));
            }
        });
    }



    // you should extends CircularAdapter to add your custom item
    private class CircularItemAdapter extends CircularAdapter {

        private ArrayList<String> mItems;
        private LayoutInflater mInflater;
        private ArrayList<View> mItemViews;

        public CircularItemAdapter(LayoutInflater inflater, ArrayList<String> items){
            this.mItemViews = new ArrayList<>();
            this.mItems = items;
            this.mInflater = inflater;

            for(final String s : mItems){
                View view = mInflater.inflate(R.layout.view_circular_item, null);
                TextView itemView = (TextView) view.findViewById(R.id.bt_item);
                itemView.setText(s);
                mItemViews.add(view);
            }
        }

        @Override
        public ArrayList<View> getAllViews() {
            return mItemViews;
        }

        @Override
        public int getCount() {
            return mItemViews.size();
        }

        @Override
        public View getItemAt(int i) {
            return mItemViews.get(i);
        }

        @Override
        public void removeItemAt(int i) {
            if(mItemViews.size() > 0) {
                mItemViews.remove(i);
                notifyItemChange();
            }
        }

        @Override
        public void addItem(View view) {
            mItemViews.add(view);
            notifyItemChange();
        }
    }
}
