package com.example.lisatak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView listak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listak=findViewById(R.id.listview);
        customadapter adaptak= new customadapter();
        listak.setAdapter(adaptak);
    }
    class customadapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return images.length;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub

            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertview, ViewGroup arg2) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = getLayoutInflater();
            convertview = inflater.inflate(R.layout.custom, null);
            TextView tv = (TextView) convertview.findViewById(R.id.textView1);
            ImageView image = (ImageView) convertview.findViewById(R.id.imageView1);
            tv.setText(names[position]);
            image.setImageResource(images[position]);

            return convertview;
        }

    }

    String[] names = { "name1", "name2", "name3", "name4", "name5" };
    int[] images = { R.drawable.bin, R.drawable.bin, R.drawable.bin,
            R.drawable.bin, R.drawable.bin };
}
