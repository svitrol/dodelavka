package com.example.customlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class MainActivity : AppCompatActivity() {
    ListView listak;
    internal var images = intArrayOf(R.drawable.bin);
    internal var nazvy = arrayOf("bin");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listak = findViewById(R.id.listView)
        val adaptak = CustomAdapter()
        listak.setAdapter(adaptak)
    }
    internal inner class CustomAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return images.size
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var convertView = convertView
            convertView = layoutInflater.inflate(R.layout.custom, null)
            val imageView = findViewById(R.id.imageView2)
            val textView = findViewById(R.id.textView_name)
            imageView.setImageResource(images[position])
            textView.setText(nazvy[position])
            return convertView
        }
    }
}
