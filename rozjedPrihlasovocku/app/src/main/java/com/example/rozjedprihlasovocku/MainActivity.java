package com.example.rozjedprihlasovocku;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, dialogNoveMistnosti.ExampleDialogListener{
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    GridView gridView;
    List<obrazZmistnosti> mistnustky=new ArrayList<>();
    Uzivatel Pepan=null;

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
        gridView = findViewById(R.id.gridview);
        mistnustky.add(new obrazZmistnosti(Uri.parse("android.resource://com.example.rozjedprihlasovocku/" + android.R.drawable.ic_input_add),Uri.parse("android.resource://com.example.rozjedprihlasovocku/" + android.R.drawable.ic_input_add),"Add"));


        Intent sCimPrisel=getIntent();
        Pepan=(Uzivatel) sCimPrisel.getSerializableExtra("Uzivatelos");
        if(Pepan!=null){
            if(Pepan.getMistnosti()!=null){
                String znova="";
                final File[] files = getFilesDir().listFiles();
                for(int i=0;i<files.length;i++){
                    String prvni=files[i].getName().split("\\.")[0];
                    if(prvni.equals(""))files[i].delete();
                    else znova+=prvni+":";
                }
                znova=znova.substring(0,znova.length()-1);
                Pepan.setMistnosti(znova);
                final String[] mistnosti=Pepan.getMistnosti().split(":");


                for(int i=0;i<mistnosti.length;i++){
                    int icko=0;
                    for(;icko<files.length;icko++){
                        String prvnicast=files[icko].getName().split("\\.")[0];
                        if(prvnicast.equals(mistnosti[i])){
                            Toast.makeText(this,"jo našel jsem aspoň jednu shodu",Toast.LENGTH_LONG).show();
                            break;
                        }

                    }

                    mistnustky.add(0,new obrazZmistnosti(Uri.fromFile(files[icko]),Uri.fromFile(files[icko]),mistnosti[i]));
                }
            }

        }
        else{
            Intent intent=new Intent(this,login.class);
            startActivity(intent);
            finish();
        }
        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),fruitNames[i],Toast.LENGTH_LONG).show();
                if(i==mistnustky.size()-1){
                    openDialog();

                }
                else{
                    Intent intent = new Intent(getApplicationContext(),Mistnost.class);
                    intent.putExtra("obrazek",mistnustky.get(i).odkazNaObraz);
                    intent.putExtra("nazve",mistnustky.get(i).nazevMistnosti);
                    intent.putExtra("Uzivatelos",getIntent().getSerializableExtra("Uzivatelos"));
                    startActivity(intent);
                }


            }
        });
    }
    public void openDialog() {
        dialogNoveMistnosti exampleDialog = new dialogNoveMistnosti();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    @Override
    protected void onStop() {
        super.onStop();
        String AktualniMistnosti="";
        for(int i=0;i<mistnustky.size()-1;i++){
            AktualniMistnosti+=mistnustky.get(i).nazevMistnosti+":";
        }
        AktualniMistnosti=AktualniMistnosti.substring(0,AktualniMistnosti.length()-1);
        if(!AktualniMistnosti.equals(Pepan.getMistnosti())){
            Pepan.setMistnosti(AktualniMistnosti);
            Pepan.updateTask(this);
        }

    }
    String posledniCeskyNazve="";
    @Override
    public void applyTexts(String nezevNoveMistnosti) {
        posledniCeskyNazve=nezevNoveMistnosti;
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
    private void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            Uri uri = data.getData();
            //set image to image view
            mistnustky.add(0,new obrazZmistnosti(uri,uri,posledniCeskyNazve));
            ((CustomAdapter)gridView.getAdapter()).Aktualizovat();
            String path = uri.getPath();//getRealPathFromURI(this,uri);
            String name = posledniCeskyNazve+".jpg";
            File novy=new File(getFilesDir(),name);
            File stary=new File(uri.getPath());


            try {
                copyFileUsingStream(stary,novy);
                //insertInPrivateStorage(name,path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void insertInPrivateStorage(String name, String path) throws IOException {
        FileOutputStream fos  = openFileOutput(name,MODE_APPEND);

        File file = new File(path);

        byte[] bytes = getBytesFromFile(file);

        fos.write(bytes);
        fos.close();

        Toast.makeText(getApplicationContext(),"File saved in :"+ getFilesDir() + "/"+name,Toast.LENGTH_SHORT).show();
    }

    private byte[] getBytesFromFile(File file) throws IOException {

        byte[] data = readFileToByteArray(file);
        return data;

    }
    public static byte[] readFileToByteArray(File file) {
        byte[] fileBytes = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int offset = 0;
            int count = (int) file.length();
            int temp = 0;

            while ((temp = fis.read(fileBytes, offset, count)) > 0) {
                offset += temp;
                count -= temp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return fileBytes;
        }
    }

    private String getFileName(Uri uri)
    {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mistnustky.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void Aktualizovat()
        {
            notifyDataSetChanged();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.fruits);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(mistnustky.get(i).nazevMistnosti);
            image.setImageURI(mistnustky.get(i).odkazNaObraz);
            return view1;



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
