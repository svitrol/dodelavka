package com.example.rozjedprihlasovocku;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,dialogNoveMistnosti.ExampleDialogListener{
    List<LinearLayout> radky=new ArrayList<>();
    LinearLayout hlavniPlocha;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        hlavniPlocha=findViewById(R.id.hlavniPlocha);
        Intent sCimPrisel=getIntent();
        Uzivatel Pepan=(Uzivatel) sCimPrisel.getSerializableExtra("Uzivatelos");
        if(Pepan!=null){
            /*LinearLayout neco=findViewById(R.id.hlavicka);
            TextView jmenovka=neco.findViewById(R.id.jmenoPrijmeni);
            TextView emailovka=neco.findViewById(R.id.emailovyOkno);
            jmenovka.setText(Pepan.getJmeno()+" "+Pepan.getPrijmeni());
            emailovka.setText(Pepan.getEmail());*/


            if(Pepan.getMistnosti()!=null){
                String[] mistnosti=Pepan.getMistnosti().split(":");
                int i=0;
                for(;i<mistnosti.length;i++){
                    if(i%2==0){
                        LinearLayout novyRadek=new LinearLayout(MainActivity.this);
                        novyRadek.setOrientation(LinearLayout.HORIZONTAL);
                        hlavniPlocha.addView(novyRadek);
                        radky.add(novyRadek);
                    }
                    LayoutInflater inflater= (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View bunkaMistonsti=inflater.inflate(R.layout.bunka_mistnosti,null);
                    TextView napis= bunkaMistonsti.findViewById(R.id.nazevMistnosti);
                    napis.setText(mistnosti[i]);
                    radky.get(radky.size()-1).addView(bunkaMistonsti);
                }
                if(i%2==0){
                    LinearLayout novyRadek=new LinearLayout(MainActivity.this);
                    novyRadek.setOrientation(LinearLayout.HORIZONTAL);
                    hlavniPlocha.addView(novyRadek);
                    radky.add(novyRadek);
                }
                LayoutInflater inflater= (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View bunkaMistonsti=inflater.inflate(R.layout.bunka_mistnosti,null);
                bunkaMistonsti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                });
                radky.get(radky.size()-1).addView(bunkaMistonsti);
            }
            else{
                LinearLayout novyRadek=new LinearLayout(MainActivity.this);
                novyRadek.setOrientation(LinearLayout.HORIZONTAL);
                hlavniPlocha.addView(novyRadek);
                radky.add(novyRadek);
                LayoutInflater inflater= (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View bunkaMistonsti=inflater.inflate(R.layout.bunka_mistnosti,null);
                bunkaMistonsti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                });
                radky.get(radky.size()-1).addView(bunkaMistonsti);
            }

        }
    }
    public void openDialog() {
        dialogNoveMistnosti exampleDialog = new dialogNoveMistnosti();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    String ElMistnostVamanos;
    @Override
    public void applyTexts(String jmenoMistnosti) {
        ElMistnostVamanos=jmenoMistnosti;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                //permission not granted, request it.
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popup for runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
            else {
                //permission already granted
                pickImageFromGallery();
            }
        }
        else {
            //system os is less then marshmallow
            pickImageFromGallery();
        }


    }
    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }
    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            //set image to image view
            Uri cesta=data.getData();
            File obrazislav=new File(cesta.getPath());
            File novyObraz=new File(getDataDir(),"moje.jpg");
            try {
                copyFile(obrazislav,novyObraz);
                /*LayoutInflater inflater= (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View bunkaMistonsti=inflater.inflate(R.layout.bunka_mistnosti,null);
                TextView napis= bunkaMistonsti.findViewById(R.id.nazevMistnosti);
                ImageView ramecek=bunkaMistonsti.findViewById(R.id.thumbnail);
                ramecek.setImageURI(Uri.fromFile(novyObraz));
                napis.setText(ElMistnostVamanos);
                radky.get(radky.size()-1).addView(bunkaMistonsti);*/
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"ups something went wrong",Toast.LENGTH_LONG).show();
            }
        }
    }
    public static void copyFile(File src, File dst) throws IOException
    {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
