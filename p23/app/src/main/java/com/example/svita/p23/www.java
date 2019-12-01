package com.example.svita.p23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

public class www extends AppCompatActivity {

    WebView prohlizec;
    EditText url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_www);
        url=findViewById(R.id.editText3);
        prohlizec=findViewById(R.id.webView);
    }
    public void padej(View view){
        prohlizec.loadUrl(url.getText().toString());

    }
}
